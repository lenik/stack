package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.PartyLogCategory;

public class PartyLogCategoryDto
        extends SimpleNameDictDto<PartyLogCategory> {

    private static final long serialVersionUID = 1L;

    public PartyLogCategoryDto() {
        super();
    }

    public PartyLogCategoryDto(PartyLogCategory source) {
        super(source);
    }

}
