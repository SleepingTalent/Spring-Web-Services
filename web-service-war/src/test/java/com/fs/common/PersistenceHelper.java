package com.fs.common;

import com.fs.humanResources.common.exception.DeleteEntityException;
import com.fs.humanResources.common.exception.HolidayNotFoundException;
import com.fs.humanResources.common.exception.SaveEntityException;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class PersistenceHelper {

    private static Logger log = Logger.getLogger(PersistenceHelper.class);

    public static String getUniqueString(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static void saveEmployee(Employee employee) throws SaveEntityException {
        HibernateHelper.saveEntity(employee);
    }

    public static void deleteEmployee(Employee employee) throws DeleteEntityException {
        HibernateHelper.deleteEntity(employee);
    }

    public static void deleteHoliday(Holiday holiday) throws DeleteEntityException {
        HibernateHelper.deleteEntity(holiday);
    }

    public static List<Holiday> findHolidays(Employee employee) throws HolidayNotFoundException {
        boolean rollback = false;
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {

            Criteria criteria =  HibernateHelper.getSessionFactory().getCurrentSession().createCriteria(Holiday.class);
            criteria.add(Restrictions.eq("employee", employee));
            List<Holiday> results=  criteria.list();

            if (results == null && results.isEmpty()) {
                throw new HolidayNotFoundException();
            }

            return results;
        }catch (Exception ex) {
            log.error(ex);
            rollback = true;
            throw new HolidayNotFoundException();
        }finally {
            HibernateHelper.handleTransaction(rollback, transaction);
        }
    }

    public static Employee findEmployee(Long id) {
        boolean rollback = false;
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            return (Employee) session.get(Employee.class, id);
        } catch (HibernateException he) {
            log.error(he);
            throw new EntityNotFoundException();
        }  finally {
            HibernateHelper.handleTransaction(rollback, transaction);
        }
    }
}
