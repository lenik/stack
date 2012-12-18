package com.bee32.sem.people.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.entity.ContactCategory;

public class ContactCategoryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ContactCategoryAdminBean() {
        super(ContactCategory.class, ContactCategoryDto.class, 0);
    }

}
