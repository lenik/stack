package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.PartyRecordCategory;

public class PartyRecordCategoryDto
        extends SimpleNameDictDto<PartyRecordCategory> {

    private static final long serialVersionUID = 1L;

    public PartyRecordCategoryDto() {
        super();
    }

    public PartyRecordCategoryDto(PartyRecordCategory source) {
        super(source);
    }

}
