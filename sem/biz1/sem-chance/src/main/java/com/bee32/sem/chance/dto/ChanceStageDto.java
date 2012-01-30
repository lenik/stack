package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.chance.entity.ChanceStage;

public class ChanceStageDto
        extends SimpleNameDictDto<ChanceStage> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(ChanceStage source) {
        super._marshal(source);
    }

    @Override
    protected void _unmarshalTo(ChanceStage target) {
        super._unmarshalTo(target);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

}
