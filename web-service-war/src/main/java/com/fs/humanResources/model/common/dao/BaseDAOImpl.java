package com.fs.humanResources.model.common.dao;

import com.fs.humanResources.common.exception.DeleteEmployeeException;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.SaveEmployeeException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

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
    public E findById(I id) throws EmployeeNotFoundException {
        Session session = getCurrentSession();
        Transaction tx = null;
        boolean rollback = false;

        try {
            tx = session.beginTransaction();
            return (E) getCurrentSession().get(entityClass, id);
        } catch (RuntimeException re) {
            rollback = true;
            throw new EmployeeNotFoundException();
        }finally {
           if(rollback) {
               tx.rollback();
           }else {
               tx.commit();
           }
        }
    }

    @Override
    public void saveOrUpdate(E entity) throws SaveEmployeeException {
        Session session = getCurrentSession();
        Transaction tx = null;
        boolean rollback = false;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
        } catch (RuntimeException re) {
            rollback = true;
            throw new SaveEmployeeException();
        } finally {
        if(rollback) {
            tx.rollback();
        }else {
            tx.commit();
        }
    }
    }

    @Override
    public void delete(E entity) throws DeleteEmployeeException {
        Session session = getCurrentSession();
        Transaction tx = null;
        boolean rollback = false;

        try {
            tx = session.beginTransaction();
            session.delete(entity);
        } catch (RuntimeException re) {
            rollback = true;
            throw new DeleteEmployeeException();
        } finally {
        if(rollback) {
            tx.rollback();
        }else {
            tx.commit();
        }
    }
    }

    @Override
    public List findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }
}