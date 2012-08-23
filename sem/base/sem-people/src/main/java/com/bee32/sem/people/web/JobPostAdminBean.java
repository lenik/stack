package com.bee32.sem.people.web;

import com.bee32.sem.hr.dto.JobPostDto;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class JobPostAdminBean
        extends SimpleEntityViewBean {

    public JobPostAdminBean() {
        super(JobPost.class, JobPostDto.class, 0);
    }

    private static final long serialVersionUID = 1L;

}
