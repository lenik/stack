package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.Person;

public class PersonDto
        extends AbstractPersonDto<Person> {

    private static final long serialVersionUID = 1L;

    public PersonDto() {
        super();
    }

    public PersonDto(Person source) {
        super(source);
    }

    public PersonDto(int selection) {
        super(selection);
    }

    public PersonDto(int selection, Person source) {
        super(selection, source);
    }

}
