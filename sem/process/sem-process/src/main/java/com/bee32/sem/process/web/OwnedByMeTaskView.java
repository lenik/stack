package com.bee32.sem.process.web;

import org.activiti.engine.task.TaskQuery;

import com.bee32.icsf.login.SessionUser;

public class OwnedByMeTaskView
        extends AbstractTaskView {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskQuery createQuery() {
        TaskQuery query = super.createQuery();

        SessionUser sessionUser = SessionUser.getInstance();
        String myName = sessionUser.getUser().getName();

        query.taskOwner(myName);
        return query;
    }

}
