package com.mercury.rts.common.db;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<T> klass;
    private static Logger log = Logger.getLogger(GenericDaoImpl.class);

    @Autowired
    private SessionFactory appSessionFactory;
    public GenericDaoImpl() {}
    public GenericDaoImpl(Class<T> klass) {
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
            return new SessionInfo(appSessionFactory.getCurrentSession(), false, false);
        } catch (HibernateException he) {
            log.debug(he);
            return new SessionInfo(appSessionFactory.openSession(), false, false);
        }
    }

	public void setAppSessionFactory(SessionFactory appSessionFactory) {
		this.appSessionFactory = appSessionFactory;
	}

	public SessionFactory getAppSessionFactory() {
		return appSessionFactory;
	}

}
