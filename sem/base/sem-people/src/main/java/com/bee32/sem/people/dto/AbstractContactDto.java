package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactXP;

public class AbstractContactDto<E extends Contact>
        extends EntityExtDto<E, Long, ContactXP> {

    private static final long serialVersionUID = 1L;

    public AbstractContactDto() {
        super();
    }

    public AbstractContactDto(E source) {
        super(source);
    }

    public AbstractContactDto(int selection) {
        super(selection);
    }

    public AbstractContactDto(int selection, E source) {
        super(selection, source);
    }

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
    protected void _marshal(E source) {
    }

    @Override
    protected void _unmarshalTo(E target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
