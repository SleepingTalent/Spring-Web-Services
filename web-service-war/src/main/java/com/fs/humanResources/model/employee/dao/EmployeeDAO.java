package com.fs.humanResources.model.employee.dao;

import com.fs.humanResources.common.exception.DeleteEmployeeException;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.SaveEmployeeException;
import com.fs.humanResources.model.employee.entities.Employee;

public interface EmployeeDAO {
    Employee findEmployee(Long employeeId) throws EmployeeNotFoundException;
    void saveEmployee(Employee employee) throws SaveEmployeeException;
    void deleteEmployee(Employee employee) throws DeleteEmployeeException;
}
