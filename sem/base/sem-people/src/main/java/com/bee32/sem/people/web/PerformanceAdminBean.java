package com.bee32.sem.people.web;

import com.bee32.sem.hr.dto.JobPerformanceDto;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PerformanceAdminBean
        extends SimpleEntityViewBean {

    public PerformanceAdminBean() {
        super(JobPerformance.class, JobPerformanceDto.class, 0);
    }

    private static final long serialVersionUID = 1L;

}
