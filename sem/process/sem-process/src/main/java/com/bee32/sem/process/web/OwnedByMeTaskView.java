package com.bee32.sem.process.web;

import org.activiti.engine.task.TaskQuery;

public class OwnedByMeTaskView
        extends AbstractTaskView {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskQuery createQuery() {
        TaskQuery query = super.createQuery();
        query.taskOwner(getLoggedInUserName());
        return query;
    }

}
