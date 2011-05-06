package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.ContactCategory;

public class ContactCategoryDto
        extends SimpleNameDictDto<ContactCategory> {

    private static final long serialVersionUID = 1L;

    public ContactCategoryDto() {
        super();
    }

    public ContactCategoryDto(ContactCategory source) {
        super(source);
    }

}
