package com.bee32.sem.track.web;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.entity.Issue;

public abstract class AbstractIssueView
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AbstractIssueView(ICriteriaElement... criteriaElements) {
        super(Issue.class, IssueDto.class, 0, criteriaElements);
    }

}
