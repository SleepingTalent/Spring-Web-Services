package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.model.common.dao.BaseDAOImpl;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.employee.exception.EmployeeNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


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
                throw new EmployeeNotFoundException("Employee Not Found with Id : " + employeeId);
            }
            return employee;
        } catch (EntityNotFoundException enfe) {
            log.error("Employee Not Found with Id : " + employeeId, enfe);
            throw new EmployeeNotFoundException("Employee Not Found with Id : " + employeeId);
        }
    }
}
