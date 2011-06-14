package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.PersonSidType;

public class PersonSidTypeDto extends SimpleNameDictDto<PersonSidType> {

    private static final long serialVersionUID = 1L;

    public PersonSidTypeDto() {
        super();
    }

    public PersonSidTypeDto(PersonSidType source) {
        super(source);
    }

}
