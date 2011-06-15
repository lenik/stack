package com.bee32.sem.people.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.people.entity.OrgType;

public class OrgTypeDto extends SimpleNameDictDto<OrgType> {

    private static final long serialVersionUID = 1L;

    public OrgTypeDto() {
        super();
    }

    public OrgTypeDto(OrgType source) {
        super(source);
    }
}
