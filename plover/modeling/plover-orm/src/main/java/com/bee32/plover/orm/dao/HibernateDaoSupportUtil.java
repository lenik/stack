package com.bee32.plover.orm.dao;

import org.hibernate.Session;

public class HibernateDaoSupportUtil
        extends HibernateDaoSupport {

    /**
     * @see #getSession()
     */
    public Session _getSession() {
        return super.getSession();
    }

    public Session _getSession(boolean allowCreate) {
        return super.getSession(allowCreate);
    }

}
