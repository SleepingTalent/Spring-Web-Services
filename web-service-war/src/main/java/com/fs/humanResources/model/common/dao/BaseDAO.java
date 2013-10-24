package com.fs.humanResources.model.common.dao;

import org.hibernate.criterion.Criterion;

import java.util.List;

public interface BaseDAO<E, I> {

    public E findById(I id);
    public void saveOrUpdate(E e);
    public void delete(E e);
    public List findByCriteria(Criterion criterion);
}
