package com.bee32.sem.make.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.make.entity.MakeStepName;

public class MakeStepNameDto
        extends UIEntityDto<MakeStepName, Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(MakeStepName source) {

    }

    @Override
    protected void _unmarshalTo(MakeStepName target) {

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

}
