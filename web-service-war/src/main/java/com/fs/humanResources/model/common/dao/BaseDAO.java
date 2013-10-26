package com.fs.humanResources.model.common.dao;

import com.fs.humanResources.common.exception.*;
import org.hibernate.criterion.Criterion;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface BaseDAO<E, I> {

    public E findById(I id) throws EntityNotFoundException;
    public void saveOrUpdate(E e) throws SaveEntityException;
    public void delete(E e) throws DeleteEntityException;
    public List<E> findByCriteria(Criterion criterion);
}
