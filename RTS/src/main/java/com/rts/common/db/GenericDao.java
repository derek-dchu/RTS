package com.rts.common.db;

import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    List<T> findAllByMulti(Map<String, Object> condition);
    T findBy(String property, Object value);
    void delete(T object);
}