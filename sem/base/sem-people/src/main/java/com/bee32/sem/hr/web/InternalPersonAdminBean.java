package com.bee32.sem.hr.web;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

public class InternalPersonAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public InternalPersonAdminBean() {
        super(Person.class, PersonDto.class, 0, Order.desc("id"), PeopleCriteria.internal());
    }

}
