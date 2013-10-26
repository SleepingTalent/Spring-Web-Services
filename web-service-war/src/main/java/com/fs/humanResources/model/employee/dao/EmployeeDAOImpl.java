package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.common.exception.*;
import com.fs.humanResources.model.common.dao.BaseDAOImpl;
import com.fs.humanResources.model.employee.entities.Employee;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class EmployeeDAOImpl extends BaseDAOImpl<Employee, Long> implements EmployeeDAO {

    Logger log = Logger.getLogger(EmployeeDAOImpl.class);

    @Autowired
    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        super(Employee.class, sessionFactory);
    }

    @Override
    public Employee findEmployee(Long employeeId) throws EmployeeNotFoundException {
        try {
            Employee employee = findById(employeeId);

            if (employee == null) {
                throw new EmployeeNotFoundException();
            }
            return employee;
        } catch (HibernateException he) {
            log.error("Employee Not Found with Id : " + employeeId, he);
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public void saveEmployee(Employee employee) throws SaveEmployeeException {
        try {
            saveOrUpdate(employee);
        } catch (SaveEntityException e) {
            throw new SaveEmployeeException();
        }
    }

    @Override
    public void deleteEmployee(Employee employee) throws DeleteEmployeeException {
        try {
            delete(employee);
        } catch (DeleteEntityException e) {
            throw new DeleteEmployeeException();
        }
    }
}
