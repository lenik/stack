package com.bee32.sem.process.web;

import org.activiti.engine.history.HistoricProcessInstanceQuery;

import com.bee32.icsf.login.SessionUser;

public class MyProcessInstanceView
        extends HistoricProcessInstanceView {

    private static final long serialVersionUID = 1L;

    @Override
    protected HistoricProcessInstanceQuery createQuery() {
        HistoricProcessInstanceQuery query = super.createQuery();

        SessionUser sessionUser = SessionUser.getInstance();
        String myName = sessionUser.getUser().getName();
        query.startedBy(myName);

        return query;
    }

    @Override
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        throw new UnsupportedOperationException("Historic process instance is read-only.");
    }

}
