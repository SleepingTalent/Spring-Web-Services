package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.model.employee.entities.Employee;
import org.springframework.stereotype.Repository;
import com.fs.humanResources.model.common.dao.BaseDAOImpl;


@Repository
public class EmployeeDAOImpl extends BaseDAOImpl<Employee,Long> implements EmployeeDAO {

    protected EmployeeDAOImpl() {
        super(Employee.class);
    }

    @Override
    public Employee findEmployee(Long employeeId) throws EmployeeNotFoundException {
        Employee employee  = findById(employeeId);

        if(employee == null) {
            throw new EmployeeNotFoundException();
        }

        return employee;
    }
}
