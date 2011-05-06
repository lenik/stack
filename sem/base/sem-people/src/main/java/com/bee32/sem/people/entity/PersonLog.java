package com.bee32.sem.people.entity;

import com.bee32.plover.orm.entity.EntityBean;

public class PersonLog
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    Person person;
    PersonLogCategory category;
    String description;

}
