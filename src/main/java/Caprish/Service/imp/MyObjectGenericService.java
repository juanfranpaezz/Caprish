package Caprish.Service.imp;

import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public abstract class MyObjectGenericService<T extends MyObject> {

    @PersistenceContext
    protected EntityManager em;

    protected final MyObjectGenericRepository<T, Long> repository;

    protected MyObjectGenericService(MyObjectGenericRepository<T, Long> childRepository) {
        this.repository = childRepository;
    }


    @Transactional
    protected int updateField(Long id, String fieldName, Object value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<T> update = cb.createCriteriaUpdate(getEntityClass());
        Root<T> root = update.from(getEntityClass());
        update.set(root.get(fieldName), value);
        update.where(cb.equal(root.get("id"), id));
        return em.createQuery(update).executeUpdate();
    }

    protected abstract Class<T> getEntityClass();


    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
