package com.bee32.sem.attendance.dto;

import java.util.Map;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;

public class AttendanceMRecordDto
        extends UIEntityDto<AttendanceMRecord, Long> {

    private static final long serialVersionUID = 1L;

    Map<Integer, AttendanceDRecord> recoreds;

    @Override
    protected void _marshal(AttendanceMRecord source) {
        recoreds = source.warpToAttendanceMap();
    }

    @Override
    protected void _unmarshalTo(AttendanceMRecord target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
