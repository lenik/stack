package com.bee32.sem.attendance.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.Pair;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.attendance.util.AttendanceDRecord;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMRecordDto
        extends UIEntityDto<AttendanceMRecord, Long> {

    private static final long serialVersionUID = 1L;

    int year;
    int month;
    EmployeeInfoDto employee;
    boolean safe;
    String attendanceData;

    public AttendanceMRecordDto() {
        int yearMonth = SalaryDateUtil.convertToYearMonth(new Date());
        this.year = yearMonth / 100;
        this.month = yearMonth % 100;
    }

    @Override
    protected void _marshal(AttendanceMRecord source) {
        year = source.getYearMonth() / 100;
        month = source.getYearMonth() % 100;
        employee = mref(EmployeeInfoDto.class, source.getEmployee());
        attendanceData = source.getAttendanceData();
        safe = source.isSafe();
        if (attendanceData == null)
            attendanceData = generatorDefaultAttendanceData(year, month);
    }

    @Override
    protected void _unmarshalTo(AttendanceMRecord target) {
        target.setYearMonth(year * 100 + month);
        merge(target, "employee", employee);
        target.setSafe(safe);
        target.setAttendanceData(attendanceData);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public static String generatorDefaultAttendanceData(int year, int month) {
        int days = SalaryDateUtil.getDayNumberOfMonth(year, month);
        StringBuilder sb = new StringBuilder(days * 10);
        for (int day = 1; day <= days; day++) {
            if (day < 10)
                sb.append('0');
            sb.append(day);
            sb.append("A.A.-.");
            sb.append(".,");
        }
        String attendanceData = sb.toString();
        return attendanceData;
    }

    public static List<List<AttendanceDRecord>> calendarView(int yearMonth, Map<Integer, AttendanceDRecord> map) {

        Pair<Integer, Integer> pair = SalaryDateUtil.getReffer(yearMonth);
        int week = pair.getSecond();
        int refer = pair.getFirst();

        // AttendanceDRecord[][] recordArray = new AttendanceDRecord[pair.getSecond()][7];
        // week * day * record
        List<List<AttendanceDRecord>> monthView = new ArrayList<List<AttendanceDRecord>>();
        List<AttendanceDRecord> weekview = null;

        for (int i = 0; i < week * 7; i++) {
            int index = i - refer + 1;

            AttendanceDRecord record = map.get(index);

            if (record == null) {
                AttendanceDRecord notavailable = new AttendanceDRecord();
                notavailable.setMorning(AttendanceType.notAvailable);
                notavailable.setAfternoon(AttendanceType.notAvailable);
                notavailable.setEvening(AttendanceType.notAvailable);
                record = notavailable;
            }

            if (i == 0) {
                weekview = new ArrayList<AttendanceDRecord>();
            }

            if (i != 0 && i % 7 == 0) {
                monthView.add(weekview);
                weekview = new ArrayList<AttendanceDRecord>();
            }

            weekview.add(record);
        }

        if (weekview.size() > 0)
            monthView.add(weekview);
        return monthView;
    }

    public static Integer[] ExtractProviderArgs(Map<Integer, AttendanceDRecord> map) {

        int shouldAttendanceNum = 0; // ?????????
        int attendanceNum = 0; // ??????
        int shouldOvertimeNum = 0; // ????????????
        int overtimeNum = 0; // ?????????
        int lateNum = 0; // ??????

        for (AttendanceDRecord attendance : map.values()) {
            AttendanceType m = attendance.getMorning();
            AttendanceType a = attendance.getAfternoon();
            AttendanceType e = attendance.getEvening();

            if (m != AttendanceType.rest2 && m != AttendanceType.notAvailable)
                shouldAttendanceNum += 1;
            if (a != AttendanceType.rest2 && a != AttendanceType.notAvailable)
                shouldAttendanceNum += 1;
            if (e != AttendanceType.notAvailable && e != AttendanceType.rest2)
                shouldOvertimeNum += 1;

            if (m.isMutiplier())
                attendanceNum += 1;
            if (a.isMutiplier())
                attendanceNum += 1;
            if (e.isMutiplier())
                overtimeNum += 1;

            if (m == AttendanceType.late1)
                lateNum += 1;
// if (a == AttendanceType.late1)
// lateNum += 1;
// if (e == AttendanceType.late1)
// lateNum += 1;

        }

        Integer[] array = new Integer[5];
        array[0] = shouldAttendanceNum;
        array[1] = attendanceNum;
        array[2] = lateNum;
        array[3] = shouldOvertimeNum;
        array[4] = overtimeNum;

        return array;
    }

    public static Map<Integer, AttendanceDRecord> wrapToAttendanceMap(String data) {
        Map<Integer, AttendanceDRecord> map = new HashMap<Integer, AttendanceDRecord>(50);

        int len = data.length();
        int day = 1;
        for (int i = 0; i < len; i += 10) {
            String dayString = data.substring(i, i + 10);
            AttendanceDRecord dayRecord = parseDayString(dayString);
            assert day == dayRecord.getDay();
            map.put(day++, dayRecord);
        }

        return map;
    }

    /**
     * The format of day string:
     *
     * DD MM AA EE R C: day morning/afternoon/evening reserved comma.
     */
    public static AttendanceDRecord parseDayString(String dayString) {
        if (dayString.length() != 10)
            throw new IllegalArgumentException("Bad day string: \"" + dayString + "\"");

        AttendanceDRecord dayRecord = new AttendanceDRecord();
        int day = Integer.parseInt(dayString.substring(0, 2));
        dayRecord.setDay(day++);

        AttendanceType morning = AttendanceType.forValue(dayString.charAt(2));
        AttendanceType afternoon = AttendanceType.forValue(dayString.charAt(4));
        AttendanceType evening = AttendanceType.forValue(dayString.charAt(6));

        dayRecord.setMorning(morning);
        dayRecord.setAfternoon(afternoon);
        dayRecord.setEvening(evening);

        return dayRecord;
    }

    public void setRecords(String data) {
        attendanceData = data;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public AttendanceDRecord getRecord(int day) {
        if (day == 0)
            return null;

        int index = (day - 1) * 10;
        String dayString = attendanceData.substring(index, index + 10);
        return parseDayString(dayString);
    }

    public Integer[] getArguments() {
        Map<Integer, AttendanceDRecord> records = wrapToAttendanceMap(attendanceData);
        return ExtractProviderArgs(records);
    }

    public List<List<AttendanceDRecord>> getMonthView() {
        Map<Integer, AttendanceDRecord> records = wrapToAttendanceMap(attendanceData);
        return calendarView(year * 100 + month, records);
    }

    public String getAttendanceData() {
        return attendanceData;
    }

    public void setAttendanceData(String attendanceData) {
        this.attendanceData = attendanceData;
    }

    public String getYearMonthString() {
        return year + "???" + month + "???";
    }
}
