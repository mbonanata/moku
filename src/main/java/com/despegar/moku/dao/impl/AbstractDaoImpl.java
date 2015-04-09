package com.despegar.moku.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.despegar.moku.dao.AbstractDao;
import com.despegar.moku.model.Model;

public abstract class AbstractDaoImpl<T extends Model> implements AbstractDao<T> {

    private Class<T> clazz;
	
    @PersistenceContext
    private EntityManager entityManager;
	
    protected AbstractDaoImpl(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}
	
	@Override
	public T findById(Long id) {
        return entityManager.find(clazz, id);
	}
	
	@Override
	public void save(T model) {
		if (model.getId() == null) {
			this.entityManager.persist(model);
		} else {
			this.entityManager.merge(model);
		}
	}
	
	@Override
	public void delete(T model) {
		this.entityManager.remove(model);
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
