package Caprish.Service.imp;

import Caprish.Exception.EntityNotFoundCustomException;
import Caprish.Exception.InvalidIdException;
import Caprish.Exception.InvalidUpdateFieldException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class MyObjectGenericService<M extends MyObject, R extends MyObjectGenericRepository<M>, S extends MyObjectGenericService<M,R,S>> {

    @PersistenceContext
    protected EntityManager em;

    protected final R repository;
    

    protected MyObjectGenericService(R childRepository) {
        this.repository = childRepository;
    }

    protected abstract void verifySpecificAttributes(M entity);


    @Transactional
    protected int updateField(Long id, String fieldName, Object value) {
        if (id == null) {
            throw new InvalidUpdateFieldException("El ID es inválido.");
        }
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new InvalidUpdateFieldException("El nombre del campo es inválido.");
        }

        if (value == null) {
            throw new InvalidUpdateFieldException("El valor no puede ser null.");
        }

        if (!BeanUtils.getPropertyNames(getEntityClass()).contains(fieldName)) {
            throw new IllegalArgumentException("Campo inválido: " + fieldName);
        }


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
        CriteriaUpdate<M> update = cb.createCriteriaUpdate(getEntityClass());
        Root<M> root = update.from(getEntityClass());
        update.set(root.get(fieldName), value);
        update.where(cb.equal(root.get("id"), id));
        return em.createQuery(update).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    protected Class<M> getEntityClass(){
        return (Class<M>) ResolvableType.forClass(AopProxyUtils.ultimateTargetClass(this))
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

    public String save(M entity){
        BeanUtils.verifyValues(entity);
        verifySpecificAttributes(entity);
        repository.save(entity);
        return "Guardado con exito";
    }



    public Optional<M> findById(Long id) {
        if (id == null) {
            throw new InvalidIdException("El ID no puede ser null");
        }
        return repository.findById(id);
    }

    public List<M> findAll() {

        return repository.findAll();
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new InvalidIdException("El ID no puede ser null");
        }
        if (!repository.existsById(id)) {
            throw new EntityNotFoundCustomException("No existe una entidad con el ID: " + id);
        }
        repository.deleteById(id);
    }


}
