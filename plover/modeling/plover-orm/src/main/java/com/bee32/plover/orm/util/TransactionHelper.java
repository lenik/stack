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

    public static void openSession(Runnable runnable) {
        SessionFactory sessionFactory = ctx.bean.getBean(SessionFactory.class);
        boolean participate = false; // Shared mode.
        FlushMode flushMode = FlushMode.MANUAL;

        if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
            // Do not modify the Session: just set the participate flag.
            participate = true;
        } else {
            Session session = SessionFactoryUtils.getSession(sessionFactory, true);
            if (flushMode != null)
                session.setFlushMode(flushMode);
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        }

        try {
            runnable.run();
        } finally {
            if (!participate) {
                SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
                        .unbindResource(sessionFactory);
                SessionFactoryUtils.closeSession(sessionHolder.getSession());
            }
        }
    }

}
