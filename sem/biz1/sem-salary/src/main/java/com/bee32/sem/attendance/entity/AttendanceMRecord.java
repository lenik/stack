package com.bee32.sem.attendance.entity;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.attendance.dto.AttendanceDRecord;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class AttendanceMRecord
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    int yearMonth;
    EmployeeInfo employee;
    boolean safe;
    String attendanceData;// eg: "1:NM,LE,HO;2:NM,NM,NM;"

    public int getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(int yearMonth) {
        this.yearMonth = yearMonth;
    }

    public EmployeeInfo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfo employee) {
        this.employee = employee;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public String getAttendanceData() {
        return attendanceData;
    }

    public void setAttendanceData(String attendanceData) {
        this.attendanceData = attendanceData;
    }

    public Map<Integer, AttendanceDRecord> warpToAttendanceMap() {
        Map<Integer, AttendanceDRecord> map = new HashMap<Integer, AttendanceDRecord>();

        for (String dayString : attendanceData.split(";")) {
            String stringday = dayString.substring(0, 1);
            int day = Integer.parseInt(stringday);

            String types = dayString.substring(2);
            char [] characterValue = types.toCharArray();

            AttendanceType morning = AttendanceType.forValue(characterValue[0]);
            AttendanceType afternoon = AttendanceType.forValue(characterValue[1]);
            AttendanceType evening = AttendanceType.forValue(characterValue[2]);
            AttendanceDRecord dayRecord = new AttendanceDRecord();
            dayRecord.setDay(day);
            dayRecord.setMorning(morning);
            dayRecord.setAfternoon(afternoon);
            dayRecord.setEvening(evening);
            map.put(day, dayRecord);
        }

        return map;
    }

    public String warpToAttendanceString(Map<Integer, AttendanceDRecord> map) {
        StringBuilder sb = new StringBuilder();
        for (AttendanceDRecord day : map.values()) {
            sb.append(day.getDay());
            sb.append(":");
            sb.append(day.getMorning().getValue());
            sb.append(",");
            sb.append(day.getAfternoon().getValue());
            sb.append(",");
            sb.append(day.getEvening().getValue());
            sb.append(";");
        }
        int index = sb.length();
        sb.deleteCharAt(index - 1);
        return sb.toString();
    }

}
