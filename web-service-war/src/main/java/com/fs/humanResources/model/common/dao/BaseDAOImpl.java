package com.fs.humanResources.model.common.dao;

import com.fs.humanResources.common.exception.DeleteEntityException;
import com.fs.humanResources.common.exception.SaveEntityException;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDAOImpl<E, I extends Serializable> implements BaseDAO<E, I> {

    private Class<E> entityClass;

    private SessionFactory sessionFactory;

    protected BaseDAOImpl(Class<E> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E findById(I id) throws EntityNotFoundException {
        try {
            return (E) getCurrentSession().get(entityClass, id);
        } catch (HibernateException he) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void saveOrUpdate(E entity) throws SaveEntityException {
        try {
            getCurrentSession().saveOrUpdate(entity);
        } catch (HibernateException he) {
            throw new SaveEntityException();
        }
    }

    @Override
    public void delete(E entity) throws DeleteEntityException {
        try {
            getCurrentSession().delete(entity);
        } catch (HibernateException he) {
            throw new DeleteEntityException();
        }
    }

    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

}