package com.fs.common;


import com.fs.humanResources.common.exception.*;
import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class HibernateHelper {

    private static Logger log = Logger.getLogger(HibernateHelper.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = configureSessionFactory();
        }
        return sessionFactory;
    }

    public static SessionFactory configureSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Holiday.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/codeExampleDB");
        configuration.setProperty("hibernate.connection.username", "codeExample");
        configuration.setProperty("hibernate.connection.password", "codeExample");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.current_session_context_class", "thread");

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void saveEntity(Object entity) throws SaveEntityException {
        boolean rollback = false;
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
        }catch (Exception ex) {
           rollback = true;
           log.error(ex);
           throw new SaveEntityException();
        }finally {
           handleTransaction(rollback,transaction);
        }
    }

    public static void deleteEntity(Object entity) throws DeleteEntityException {
        boolean rollback = false;
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(entity);
        }catch (Exception ex) {
            log.error(ex);
            rollback = true;
            throw new DeleteEntityException();
        }finally {
            handleTransaction(rollback,transaction);
        }
    }

    public static <T> List<T> findByCriteria(Class<T> entityClass,Criterion criterion) throws EntityNotFoundException {
        boolean rollback = false;
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Criteria criteria =  HibernateHelper.getSessionFactory().getCurrentSession().createCriteria(entityClass);
            criteria.add(criterion);
            List<T> results=  criteria.list();

            if (results == null && results.isEmpty()) {
                throw new EntityNotFoundException();
            }

            return results;
        }catch (Exception ex) {
            log.error(ex);
            rollback = true;
            throw new EntityNotFoundException();
        }finally {
            HibernateHelper.handleTransaction(rollback, transaction);
        }
    }

    public static <T> T findById(Class<T> entityClass, Long id) {
        boolean rollback = false;
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            return (T) session.get(entityClass, id);
        } catch (HibernateException he) {
            log.error(he);
            throw new EntityNotFoundException();
        }  finally {
            HibernateHelper.handleTransaction(rollback, transaction);
        }
    }

    public static void handleTransaction(boolean rollback, Transaction transaction) {
        if(rollback) {
          transaction.rollback();
        } else {
          transaction.commit();
        }
    }

}
