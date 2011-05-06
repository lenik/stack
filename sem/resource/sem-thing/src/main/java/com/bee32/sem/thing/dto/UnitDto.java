package com.bee32.sem.thing.dto;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;
import com.bee32.sem.thing.entity.Unit;

public class UnitDto
        extends SimpleNameDictDto<Unit> {

    private static final long serialVersionUID = 1L;

    public UnitDto() {
        super();
    }

    public UnitDto(Unit source) {
        super(source);
    }

}
