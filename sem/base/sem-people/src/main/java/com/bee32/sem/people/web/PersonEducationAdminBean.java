package com.bee32.sem.people.web;

import com.bee32.sem.hr.dto.PersonEducationTypeDto;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PersonEducationAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PersonEducationAdminBean() {
        super(PersonEducationType.class, PersonEducationTypeDto.class, 0);
    }

}
