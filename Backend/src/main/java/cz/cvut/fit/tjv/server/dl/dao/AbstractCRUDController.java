/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.dl.dao;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractCRUDController<T> {

    private final Class<T> entityClass;
    
    @PersistenceContext
    private EntityManager entityManager;

    protected AbstractCRUDController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract Object getEntityId(T e);

    public void create(T e) {
        entityManager.persist(Objects.requireNonNull(e));
    }

    public T retrieve(Object id) {
        return entityManager.find(entityClass, id);
    }

    public Collection<T> retrieveAll() {
        return entityManager
                .createQuery(
                        entityManager.getCriteriaBuilder().createQuery(entityClass)
                ).getResultList();
    }
    
    public void updateOrCreate(T e) {
        entityManager.merge(Objects.requireNonNull(e));
    }

    public T delete(Object id) {
        T e = entityManager.find(entityClass, Objects.requireNonNull(id));
        if (e != null) {
            entityManager.remove(e);
        }
        return e;
    }

    public boolean exists(T e) {
        return entityManager
                .find(entityClass,
                        getEntityId(Objects.requireNonNull(e))
                ) != null;
    }

    public boolean existsById(Object id) {
        return entityManager.find(entityClass, Objects.requireNonNull(id)) != null;
    }
}
