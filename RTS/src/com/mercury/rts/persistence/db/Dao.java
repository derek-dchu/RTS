package com.mercury.common.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

public interface Dao<T, ID extends Serializable> {

    T findById(ID id);
    void save(T object);
    void flush();
    List<T> findAllBy(String property, List<?> values);
    List<T> findAllBy(String property, List<?> values, Order order);
    List<T> findAllBy(String property, Object value);
    List<T> findAllBy(String property, Object value, Order order);
    List<T> findAllBy4(String property,Object value,String property2,Object value2, String property3,Object value3,String property4,Object value4);
    List<T> findAllBy3(String property,Object value,String property2,Object value2, String property3,Object value3);
    List<T> findAllBy2(String property,Object value,String property2,Object value2); 
    List<T> findAll();
    List<T> findAll(Order order);
    List<T> findAllByAny(List<String> property, List<String> value );
    T findBy(String property, Object value);
    void delete(T object);
}
