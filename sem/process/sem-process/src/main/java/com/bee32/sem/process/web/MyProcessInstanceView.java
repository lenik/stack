package com.bee32.sem.process.web;

import org.activiti.engine.history.HistoricProcessInstanceQuery;

import com.bee32.icsf.login.SessionUser;

public class MyProcessInstanceView
        extends AbstractHistoricProcessInstanceView {

    private static final long serialVersionUID = 1L;

    @Override
    protected HistoricProcessInstanceQuery createQuery() {
        HistoricProcessInstanceQuery query = super.createQuery();

        SessionUser sessionUser = SessionUser.getInstance();
        String myName = sessionUser.getUser().getName();
        query.startedBy(myName);

        return query;
    }

}
