package com.bee32.sem.attendance.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.attendance.dto.AttendanceMRecordDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class AttendanceVariableProvider
        extends AbstractSalaryVariableProvider {

    String[] PARAMS = { "应出勤", "出勤", "应加班", "晚加班", "迟到", "安全生产月" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        EmployeeInfo employee = (EmployeeInfo) args.get(ARG_EMPLOYEE);
        int yearMonth = args.getInt(ARG_YEARMONTH);

        boolean defined = false;
        for (String p: PARAMS)
            defined |= (p.equals(variableName));
        if (!defined)
            return null;

        AttendanceMRecordDto dto = getDto(employee, yearMonth);
        if (dto == null)
            return new BigDecimal(0);

        switch (variableName) {
        case "应出勤":
            return new BigDecimal(dto.getArguments()[0]/2);
        case "出勤":
            return new BigDecimal(dto.getArguments()[1]/2);
        case "应加班":
            return new BigDecimal(dto.getArguments()[2]);
        case "加班":
            return new BigDecimal(dto.getArguments()[3]);
        case "迟到":
            return new BigDecimal(dto.getArguments()[4]);
        case "安全生产月":
//            AttendanceMRecordDto dto = getDto(employee, yearMonth);
            return new BigDecimal(30);
        default:
            return null;
        }
    }

    @Override
    public String[] getVariableNames() {
        String[] variables = new String[PARAMS.length];
        int length = PARAMS.length;
        for (int i = 0; i < length; i++) {
            variables[i] = "@" + PARAMS[i];
        }
        return variables;
    }

    static AttendanceMRecordDto getDto(EmployeeInfo employee, int yearMonth) {
            AttendanceMRecord entity = DATA(AttendanceMRecord.class).getUnique(
                    AttendanceCriteria.listRecordByEmployee(employee.getId(), yearMonth));
        if (entity == null)
            return null;
        AttendanceMRecordDto dto = DTOs.marshal(AttendanceMRecordDto.class, entity);
        return dto;
    }

}
