package com.bee32.sem.hr.web;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(EmployeeInfo.class)
public class InternalPersonAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    PersonDto selectedPerson;

    public InternalPersonAdminBean() {
        super(Person.class, PersonDto.class, 0, Order.desc("id"), PeopleCriteria.internal());
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}
