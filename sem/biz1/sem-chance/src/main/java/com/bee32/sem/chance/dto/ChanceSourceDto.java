package com.bee32.sem.chance.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.chance.entity.ChanceSourceType;

public class ChanceSourceDto
        extends SimpleNameDictDto<ChanceSourceType> {

    private static final long serialVersionUID = 1L;

    public ChanceSourceDto() {
        super();
    }

    public ChanceSourceDto(ChanceSourceType source) {
        super(source);
    }

}
