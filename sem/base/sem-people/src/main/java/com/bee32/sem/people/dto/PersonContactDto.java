package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.PersonContact;

public class PersonContactDto
        extends AbstractContactDto<PersonContact> {

    private static final long serialVersionUID = 1L;

    public static final int PERSON = 1;

    PersonDto person;

    private PersonContactDto() {
        super();
    }

    private PersonContactDto(int selection, PersonContact source) {
        super(selection, source);
    }

    private PersonContactDto(int selection) {
        super(selection);
    }

    private PersonContactDto(PersonContact source) {
        super(source);
    }

    @Override
    protected void _marshal(PersonContact source) {
        super._marshal(source);

        if (selection.contains(PERSON))
            person = new PersonDto(source.getPerson());
    }

}
