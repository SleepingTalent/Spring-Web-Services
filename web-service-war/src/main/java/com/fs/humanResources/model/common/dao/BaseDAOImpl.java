package com.fs.humanResources.model.common.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDAOImpl<E, I extends Serializable> implements BaseDAO<E,I>{

        private Class<E> entityClass;

        protected BaseDAOImpl(Class<E> entityClass) {
            this.entityClass = entityClass;
        }

        @Autowired
        private SessionFactory sessionFactory;

        public Session getCurrentSession() {
            return sessionFactory.getCurrentSession();
        }

        @Override
        public E findById(I id) {
            return (E) getCurrentSession().get(entityClass, id);
        }

        @Override
        public void saveOrUpdate(E e) {
            getCurrentSession().saveOrUpdate(e);
        }

        @Override
        public void delete(E e) {
            getCurrentSession().delete(e);
        }

        @Override
        public List findByCriteria(Criterion criterion) {
            Criteria criteria = getCurrentSession().createCriteria(entityClass);
            criteria.add(criterion);
            return criteria.list();
        }
}