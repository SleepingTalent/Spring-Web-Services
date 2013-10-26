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
        return HibernateHelper.findByCriteria(Holiday.class,Restrictions.eq("employee", employee));
    }

    public static Employee findEmployee(Long id) {
        return HibernateHelper.findById(Employee.class,id);
    }
}
