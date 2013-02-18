package com.bee32.sem.process.web;

import java.util.Set;

import org.activiti.engine.runtime.ProcessInstanceQuery;

import com.bee32.icsf.login.SessionUser;

public class CandidateProcessInstancesView
        extends AbstractProcessInstanceView {

    private static final long serialVersionUID = 1L;

    @Override
    protected ProcessInstanceQuery createQuery() {
        ProcessInstanceQuery query = super.createQuery();

        SessionUser user = SessionUser.getInstance();
        Set<String> imNameSet = user.getImNameSet();
        System.out.println(imNameSet);

        return query;
    }

}
