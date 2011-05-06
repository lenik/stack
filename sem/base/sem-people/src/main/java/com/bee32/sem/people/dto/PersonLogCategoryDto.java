package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.PersonLogCategory;

public class PersonLogCategoryDto
        extends SimpleNameDictDto<PersonLogCategory> {

    private static final long serialVersionUID = 1L;

    public PersonLogCategoryDto() {
        super();
    }

    public PersonLogCategoryDto(PersonLogCategory source) {
        super(source);
    }

}
