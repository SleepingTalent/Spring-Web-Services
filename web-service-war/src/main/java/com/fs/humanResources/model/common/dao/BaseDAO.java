package com.fs.humanResources.model.common.dao;

import com.fs.humanResources.common.exception.DeleteEmployeeException;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.SaveEmployeeException;
import org.hibernate.criterion.Criterion;

import java.util.List;

public interface BaseDAO<E, I> {

    public E findById(I id) throws EmployeeNotFoundException;
    public void saveOrUpdate(E e) throws SaveEmployeeException;
    public void delete(E e) throws DeleteEmployeeException;
    public List findByCriteria(Criterion criterion);
}
