package com.bee32.sem.people.dto;

import java.util.List;

import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonSidType;

public class PersonDto
        extends AbstractPartyDto<Person> {

    private static final long serialVersionUID = 1L;

    PersonSidType sidType;
    List<PersonContactDto> contacts;

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

    @Override
    protected void _marshal(Person source) {
        super._marshal(source);

        sex = source.getSex();

        censusRegister = source.getCensusRegister();
        sidType = source.getSidType();

        contacts = marshalList(PersonContactDto.class, source.getContacts());

        interests = source.getInterests();
    }

}
