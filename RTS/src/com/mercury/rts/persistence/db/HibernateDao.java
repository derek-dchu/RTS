package com.mercury.common.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDao<T, ID extends Serializable> implements Dao<T, ID> {

    private Class<T> klass;
    private static Logger log = Logger.getLogger(HibernateDao.class);

    @Autowired
    private SessionFactory appSessionFactory;

    public HibernateDao(Class<T> klass) {
        this.klass = klass;
    }

    /* (non-Javadoc)
     * @see com.thg.db.Dao#findById(ID)
     */
    @SuppressWarnings("unchecked")
    public T findById(ID id) {
        SessionInfo sessionInfo = getSessionInfo();
        T object = (T) sessionInfo.getSession().get(klass, id);
        sessionInfo.cleanup();
        return object;
    }

    public void save(T object) {
        log.debug("----------->" + this.toString());
        SessionInfo sessionInfo = getSessionInfo();
        sessionInfo.getSessionForWriting().saveOrUpdate(object);
        sessionInfo.cleanup();
    }

    public void delete(T object) {
        log.debug("----------->" + this.toString());
        SessionInfo sessionInfo = getSessionInfo();
        sessionInfo.getSessionForWriting().delete(object);
        sessionInfo.cleanup();
    }

    public List<T> findAllBy(String property, List<?> values) {
//        SessionInfo sessionInfo = getSessionInfo();
//        List<T> retval = (List<T>) sessionInfo.getSession().createCriteria(klass)
//                .add(Restrictions.in(property, values))
//                .list();
//        sessionInfo.cleanup();
        return (List<T>) findAllBy(property, values, null);
    }

    @SuppressWarnings("unchecked")
	public List<T> findAllBy(String property, List<?> values, Order order) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass)
                .add(Restrictions.in(property, values));
        if (order != null) {
            criteria.addOrder(order);
        }
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
    }

    @SuppressWarnings("serial")
	public List<T> findAllBy(String property, final Object value) {
        log.debug("Turning object into list");
        return findAllBy(property, new ArrayList<Object>() { { add(value); } }, null);
    }

    @SuppressWarnings("serial")
	public List<T> findAllBy(String property, final Object value, Order order) {
        log.debug("Turning object into list");
        return findAllBy(property, new ArrayList<Object>() { { add(value); } }, order);
    }

    public List<T> findAll() {
        SessionInfo sessionInfo = getSessionInfo();
        List<T> retval = findAll(null);
        sessionInfo.cleanup();
        return retval;
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

    public T findBy(String property, final Object value) {
        List<T> results = findAllBy(property, value);
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public void flush() {
        appSessionFactory.getCurrentSession().flush();

    }

    protected SessionInfo getSessionInfo() {
        try {
            return new SessionInfo(appSessionFactory.getCurrentSession(), false);
        } catch (HibernateException he) {
            log.debug(he);
            return new SessionInfo(appSessionFactory.openSession(), false);
        }
    }

	public void setAppSessionFactory(SessionFactory appSessionFactory) {
		this.appSessionFactory = appSessionFactory;
	}

	public SessionFactory getAppSessionFactory() {
		return appSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllBy4(String property, Object value, String property2,
			Object value2, String property3, Object value3, String property4,
			Object value4) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass)
                .add(Restrictions.eq(property, value)).add(Restrictions.eq(property2, value2)).add(Restrictions.eq(property3, value3)).add(Restrictions.eq(property4, value4));
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllBy3(String property, Object value, String property2,
			Object value2, String property3, Object value3) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass)
                .add(Restrictions.eq(property, value)).add(Restrictions.eq(property2, value2)).add(Restrictions.eq(property3, value3));
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllBy2(String property, Object value, String property2,
			Object value2) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass)
                .add(Restrictions.eq(property, value)).add(Restrictions.eq(property2, value2));
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllByAny(List<String> property, List<String> value ) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass);
        for ( int i = 0; i < property.size(); i++){
        	criteria.add(Restrictions.eq(property.get(i), value.get(i)));
        }
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAlllt(String property, Object value) {
        SessionInfo sessionInfo = getSessionInfo();
        Criteria criteria = sessionInfo.getSession().createCriteria(klass);
        criteria.add(Restrictions.ge(property,value));
        List<T> retval = (List<T>) criteria.list();
        sessionInfo.cleanup();
        return retval;
    }
	
	
}
