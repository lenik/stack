package com.bee32.sem.hr.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class EmployeeInfoDto
        extends UIEntityDto<EmployeeInfo, Long> {

    private static final long serialVersionUID = 1L;

    public EmployeeInfoDto() {
        super();
    }

    public EmployeeInfoDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(EmployeeInfo source) {
    }

    @Override
    protected void _unmarshalTo(EmployeeInfo target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
