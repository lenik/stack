package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.Contact;

public class ContactDto
        extends AbstractContactDto<Contact> {

    private static final long serialVersionUID = 1L;

    public ContactDto() {
        super();
    }

    public ContactDto(Contact source) {
        super(source);
    }

    public ContactDto(int selection) {
        super(selection);
    }

    public ContactDto(int selection, Contact source) {
        super(selection, source);
    }

}
