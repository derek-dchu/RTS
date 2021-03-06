package com.rts.common.db;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionInfo {
    private Session session;
    private boolean needsTransaction;
    private boolean closeSession;
    private Transaction tx;

    private static final Logger LOG = Logger.getLogger(SessionInfo.class);

    public SessionInfo(Session session, boolean needsClosing) {
        this(session, needsClosing, needsClosing);
    }

    public SessionInfo(Session session, boolean transact, boolean closeSession) {
        this.session = session;
        this.needsTransaction = transact;
        this.closeSession = closeSession;
        if (needsTransaction) { tx = session.beginTransaction(); }
    }
    public Session getSession() {
        return session;
    }

    public Session getSessionForWriting() {
        if (session.getTransaction() == null || !session.getTransaction().isActive()) {
        	System.out.println("session = " + session.getTransaction() + session.getTransaction().isActive());
            tx = session.beginTransaction();
            System.out.println("session = " + session.getTransaction() + session.getTransaction().isActive());
            needsTransaction = true;
        }
        return session;
    }

    public void cleanup() {
        if (needsTransaction) {
            try { if(!tx.wasCommitted()) tx.commit(); } catch (HibernateException he) { System.out.println("he: " + he.getClass().getName() + " " + he.getMessage()); LOG.warn("Error commiting transaction", he); }
        }
        if (closeSession) {
            try { session.close(); } catch (HibernateException he) { LOG.warn("Error closing session", he); }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        cleanup();
        super.finalize();
    }

}
