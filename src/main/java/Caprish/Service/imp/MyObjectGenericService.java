package Caprish.Service.imp;

import Caprish.Exception.EntityNotFoundCustomException;
import Caprish.Exception.InvalidEntityException;
import Caprish.Exception.InvalidIdException;
import Caprish.Exception.InvalidUpdateFieldException;
import Caprish.Model.BeanUtils;
import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public abstract class MyObjectGenericService<T extends MyObject, R extends MyObjectGenericRepository<T, Long>> {

    @PersistenceContext
    protected EntityManager em;

    protected final R repository;
    

    protected MyObjectGenericService(R childRepository) {
        this.repository = childRepository;
    }


    @Transactional
    protected int updateField(Long id, String fieldName, Object value) {
        if (id == null) {
            throw new InvalidUpdateFieldException("El ID es inválido.");        }

        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new InvalidUpdateFieldException("El nombre del campo es inválido.");
        }

        if (value == null) {
            throw new InvalidUpdateFieldException("El valor no puede ser null.");
        }
        if (!BeanUtils.getPropertyNames(getEntityClass()).contains(fieldName)) {
            throw new IllegalArgumentException("Campo inválido: " + fieldName);
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<T> update = cb.createCriteriaUpdate(getEntityClass());
        Root<T> root = update.from(getEntityClass());
        update.set(root.get(fieldName), value);
        update.where(cb.equal(root.get("id"), id));
        return em.createQuery(update).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass(){
        return (Class<T>) ResolvableType.forClass(AopProxyUtils.ultimateTargetClass(this))
                .as(MyObjectGenericService.class).getGeneric(0).resolve();
    }

    @SuppressWarnings("unchecked")
    public int changeField(Long id, String fieldName, Object value) {
        if (!BeanUtils.getPropertyNames(getEntityClass()).contains(fieldName)) {
            throw new IllegalArgumentException("Campo inválido: " + fieldName);
        }
        return ((S) AopContext.currentProxy()).updateField(id, fieldName, value);
    }

    public boolean existsById(Long id) {
        if(id == null) {
            throw new InvalidIdException("El ID es invalido.");
        }
        return repository.existsById(id);
    }

    public final T save(T entity) {
        return repository.save(entity);
    }



    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
