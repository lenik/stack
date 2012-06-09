package com.bee32.sem.hr.web;

import java.util.List;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(EmployeeInfo.class)
public class InternalPersonAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<PersonDto> internalPersons;

    public InternalPersonAdminBean() {
        super(EmployeeInfo.class, EmployeeInfoDto.class, 0, Order.desc("id"), PeopleCriteria.listEmployeeInfo());
        internalPersons = DTOs.marshalList(PersonDto.class, ctx.data.access(Person.class).list(PeopleCriteria.internal()));
    }

    @Override
    protected boolean save(int saveFlags, String hint) {
        //TODO add filter
        return super.save(saveFlags, hint);
    }

    public List<PersonDto> getInternalPersons(){
        return internalPersons;
    }

}
