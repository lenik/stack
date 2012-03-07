package com.bee32.plover.orm.util;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionHelper {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    public static Object openSession(ISessionProcedure proc)
            throws Exception {
        SessionFactory sessionFactory = ctx.bean.getBean(SessionFactory.class);
        boolean participate = false; // Shared mode.
        FlushMode flushMode = FlushMode.MANUAL;
        Session session;

        if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
            // Do not modify the Session: just set the participate flag.
            participate = true;
            session = SessionFactoryUtils.getSession(sessionFactory, false);
        } else {
            session = SessionFactoryUtils.getSession(sessionFactory, true);
            if (flushMode != null)
                session.setFlushMode(flushMode);
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        }

        Object retval;
        try {
            retval = proc.run(session);
        } finally {
            if (!participate) {
                SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
                        .unbindResource(sessionFactory);
                SessionFactoryUtils.closeSession(sessionHolder.getSession());
            }
        }

        return retval;
    }

}
