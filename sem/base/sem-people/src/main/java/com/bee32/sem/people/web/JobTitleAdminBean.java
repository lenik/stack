package com.bee32.sem.people.web;

import com.bee32.sem.hr.dto.JobTitleDto;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class JobTitleAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    JobTitleAdminBean() {
        super(JobTitle.class, JobTitleDto.class, 0);
    }

}
