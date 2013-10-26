package com.fs.humanResources.model.common.dao;

import com.fs.humanResources.common.exception.DeleteEntityException;
import com.fs.humanResources.common.exception.SaveEntityException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        boolean rollback = false;

        try {
            return (E) getCurrentSession().get(entityClass, id);
        } catch (RuntimeException re) {
            rollback = true;
            throw new EntityNotFoundException();
        } finally {
            handleTransaction(tx, rollback);
        }
    }

    @Override
    public void saveOrUpdate(E entity) throws SaveEntityException {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        boolean rollback = false;

        try {
            session.saveOrUpdate(entity);
        } catch (RuntimeException re) {
            rollback = true;
            throw new SaveEntityException();
        } finally {
            handleTransaction(tx, rollback);
        }
    }

    @Override
    public void delete(E entity) throws DeleteEntityException {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        boolean rollback = false;

        try {
            session.delete(entity);
        } catch (RuntimeException re) {
            rollback = true;
            throw new DeleteEntityException();
        } finally {
            handleTransaction(tx, rollback);
        }
    }

    @Override
    public List<E> findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }

    private void handleTransaction(Transaction tx, boolean rollback) {
        if (tx != null) {
            if (rollback) {
                tx.rollback();
            } else {
                tx.commit();
            }
        }
    }
}