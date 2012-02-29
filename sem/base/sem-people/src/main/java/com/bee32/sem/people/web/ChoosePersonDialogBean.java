package com.bee32.sem.people.web;

import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;

public class ChoosePersonDialogBean
        extends ChoosePartyDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePersonDialogBean() {
        super(Person.class, PersonDto.class, 0);
    }

}
