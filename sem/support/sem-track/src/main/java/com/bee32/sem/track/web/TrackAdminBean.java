package com.bee32.sem.track.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.entity.Issue;

public class TrackAdminBean
        extends SimpleEntityViewBean {

    public TrackAdminBean() {
        super(Issue.class, IssueDto.class, 0);
    }

    private static final long serialVersionUID = 1L;

}
