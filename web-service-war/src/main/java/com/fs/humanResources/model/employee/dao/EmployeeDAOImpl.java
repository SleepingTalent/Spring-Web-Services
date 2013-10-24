package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.common.exception.DeleteEmployeeException;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.SaveEmployeeException;
import com.fs.humanResources.model.employee.entities.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fs.humanResources.model.common.dao.BaseDAOImpl;


@Repository
public class EmployeeDAOImpl extends BaseDAOImpl<Employee,Long> implements EmployeeDAO {

    @Autowired
    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        super(Employee.class,sessionFactory);
    }

    @Override
    public Employee findEmployee(Long employeeId) throws EmployeeNotFoundException {
        Employee employee  = findById(employeeId);

        if(employee == null) {
            throw new EmployeeNotFoundException();
        }

        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) throws SaveEmployeeException {
        saveOrUpdate(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) throws DeleteEmployeeException {
        delete(employee);
    }
}
