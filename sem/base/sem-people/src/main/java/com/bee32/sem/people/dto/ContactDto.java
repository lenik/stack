package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactXP;
import com.bee32.sem.people.entity.Person;

public class ContactDto
        extends EntityExtDto<Contact, Long, ContactXP> {

    public static final int PERSON = 1;

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

    AbstractPersonDto person;
    ContactCategoryDto category;

    String email;
    String website;
    String qq;
    String workTel;
    String homeTel;
    String mobileTel;
    String fax;

    String address;
    String postCode;

    @Override
    protected void _marshal(Contact source) {
        if (selection.contains(PERSON))
            person = new AbstractPersonDto<Person>();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Contact target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
