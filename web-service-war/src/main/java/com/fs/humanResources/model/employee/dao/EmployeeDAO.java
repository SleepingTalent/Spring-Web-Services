package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.employee.exception.EmployeeNotFoundException;

public interface EmployeeDAO {
    Employee findEmployee(Long employeeId) throws EmployeeNotFoundException;
}
