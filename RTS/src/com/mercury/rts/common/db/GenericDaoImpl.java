package com.mercury.rts.common.db;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<T> klass;
    private static Logger log = Logger.getLogger(GenericDaoImpl.class);

    private SessionFactory sessionFactory;
    public GenericDaoImpl() {}
    public GenericDaoImpl(SessionFactory sessionFactory, Class<T> klass) {
        this.sessionFactory = sessionFactory;
        this.klass = klass;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public T findById(ID id) {
        T object = (T) sessionFactory.getCurrentSession().get(klass, id);
        return object;
    }

    @Transactional
    public void save(T object) {
        log.debug("----------->" + this.toString());
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    @Transactional
    public void delete(T object) {
        log.debug("----------->" + this.toString());
        sessionFactory.getCurrentSession().delete(object);
    }

    @Transactional
    @SuppressWarnings("unchecked")
	public List<T> findAllByMulti(Map<String, Object> condition) {
    	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(klass);
    	for(Map.Entry<String, Object> e : condition.entrySet()) {
    		if(e.getKey() != null) {
    		criteria.add(Restrictions.eq(e.getKey(), e.getValue()));
    		}
    	}
    	return (List<T>) criteria.list();
    }

    @Transactional
    public List<T> findAllBy(String property, List<?> values) {
        return (List<T>) findAllBy(property, values, null);
    }

    @Transactional
    @SuppressWarnings("unchecked")
	public List<T> findAllBy(String property, List<?> values, Order order) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(klass)
                .add(Restrictions.in(property, values));
        if (order != null) {
            criteria.addOrder(order);
        }
        return (List<T>) criteria.list();
    }

    @Transactional
    @SuppressWarnings("serial")
	public List<T> findAllBy(String property, final Object value) {
        log.debug("Turning object into list");
        return findAllBy(property, new ArrayList<Object>() { { add(value); } }, null);
    }

    @Transactional
    @SuppressWarnings("serial")
	public List<T> findAllBy(String property, final Object value, Order order) {
        log.debug("Turning object into list");
        return findAllBy(property, new ArrayList<Object>() { { add(value); } }, order);
    }

    @Transactional
    public List<T> findAll() {
        return findAll(null);
    }

    @SuppressWarnings("unchecked")
	public List<T> findAll(Order order) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass);
        if (order != null) {
            criteria.addOrder(order);
        }
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
    }

    @Transactional
    public T findBy(String property, final Object value) {
        List<T> results = findAllBy(property, value);
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
