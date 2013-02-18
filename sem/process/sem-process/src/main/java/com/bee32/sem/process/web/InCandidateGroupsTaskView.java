package com.bee32.sem.process.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activiti.engine.task.TaskQuery;

import com.bee32.icsf.login.SessionUser;

public class InCandidateGroupsTaskView
        extends AbstractTaskView {

    private static final long serialVersionUID = 1L;

    @Override
    protected TaskQuery createQuery() {
        TaskQuery query = super.createQuery();

        SessionUser sessionUser = SessionUser.getInstance();
        Set<String> imNameSet = sessionUser.getImNameSet();

        List<String> candidateGroupNames = new ArrayList<String>(imNameSet);
        query.taskCandidateGroupIn(candidateGroupNames);
        return query;
    }

}
