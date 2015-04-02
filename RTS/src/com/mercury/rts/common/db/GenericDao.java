package com.mercury.rts.common.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

public interface GenericDao<T, ID extends Serializable> {

    T findById(ID id);
    void save(T object);
    void flush();
    List<T> findAllBy(String property, List<?> values);
    List<T> findAllBy(String property, List<?> values, Order order);
    List<T> findAllBy(String property, Object value);
    List<T> findAllBy(String property, Object value, Order order);
    List<T> findAll();
    List<T> findAll(Order order);
    T findBy(String property, Object value);
    void delete(T object);
}