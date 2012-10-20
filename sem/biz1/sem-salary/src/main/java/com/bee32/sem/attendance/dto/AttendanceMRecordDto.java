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
import com.bee32.sem.attendance.web.AttendanceMAdmin;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMRecordDto
        extends UIEntityDto<AttendanceMRecord, Long> {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULTATTENDANCEDATA = "1:A,A,A;2:A,A,A;3:A,A,A;4:A,A,A;5:A,A,A;6:A,A,A;7:A,A,A;8:A,A,A;9:A,A,A;10:A,A,A;11:A,A,A;12:A,A,A;13:A,A,A;14:A,A,A;15:A,A,A;16:A,A,A;17:A,A,A;18:A,A,A;19:A,A,A;20:A,A,A;21:A,A,A;22:A,A,A;23:A,A,A;24:A,A,A;25:A,A,A;26:A,A,A;27:A,A,A;28:A,A,A;29:A,A,A;30:A,A,A;31:A,A,A";
    public static final String[] weekday = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

    int tmpId;
    int year;
    int month;
    EmployeeInfoDto employee;
    boolean safe;
    Map<Integer, AttendanceDRecord> records;
    Integer[] providerArgs;
    List<AttendanceDRecord> attendances;
    String attendanceData;

    List<List<AttendanceDRecord>> monthView;

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
        records = warpToAttendanceMap(source.getAttendanceData() == null ? DEFAULTATTENDANCEDATA : //
                source.getAttendanceData());
        attendances = new ArrayList<AttendanceDRecord>(records.values());
        providerArgs = ExtractProviderArgs(records);
        attendanceData = source.getAttendanceData();
    }

    @Override
    protected void _unmarshalTo(AttendanceMRecord target) {
        target.setYearMonth(year * 100 + month);
        merge(target, "employee", employee);
        target.setAttendanceData(attendanceData);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public static List<List<AttendanceDRecord>> calendarView(int yearMonth, Map<Integer, AttendanceDRecord> map) {

        Pair<Integer, Integer> pair = SalaryDateUtil.getReffer(yearMonth);
        int week = pair.getSecond();
        int refer = pair.getFirst();

// AttendanceDRecord[][] recordArray = new AttendanceDRecord[pair.getSecond()][7];
        List<List<AttendanceDRecord>> monthView = new ArrayList<List<AttendanceDRecord>>();
        List<AttendanceDRecord> weekview = null;

        for (int i = 0; i < week * 7; i++) {
            int index = i - refer + 1;

            AttendanceDRecord record = map.get(index);

            if (record == null) {
                AttendanceDRecord notavailable = new AttendanceDRecord();
                notavailable.setStyleClass(AttendanceMAdmin.calendarViewStyleClass[0]);
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

            record.setWeekday_zhcn(weekday[i % 7]);
            weekview.add(record);
        }

        monthView.add(weekview);
        return monthView;
    }

    public static Integer[] ExtractProviderArgs(Map<Integer, AttendanceDRecord> map) {

        int shouldAttendanceNum = 0; // 应出勤
        int attendanceNum = 0; // 出勤
        int shouldOvertimeNum = 0; // 应晚加班
        int overtimeNum = 0; // 晚加班
        int lateNum = 0; // 迟到

        for (AttendanceDRecord attendance : map.values()) {
            AttendanceType m = attendance.getMorning();
            AttendanceType a = attendance.getAfternoon();
            AttendanceType e = attendance.getEvening();
            if (m.getValue() != 'Z')
                shouldAttendanceNum += 1;
            if (a.getValue() != 'Z')
                shouldAttendanceNum += 1;

            if (m.isMutiplier())
                attendanceNum += 1;
            if (a.isMutiplier())
                attendanceNum += 1;

            if (m.getValue() == 'F')
                lateNum += 1;
            if (a.getValue() == 'F')
                lateNum += 1;

            if (e.getValue() != 'Y')
                shouldOvertimeNum += 1;
            if (e.isMutiplier())
                overtimeNum += 1;

        }

        Integer[] array = new Integer[5];
        array[0] = shouldAttendanceNum;
        array[1] = attendanceNum;
        array[2] = lateNum;
        array[3] = shouldOvertimeNum;
        array[4] = overtimeNum;

        return array;
    }

    public static Map<Integer, AttendanceDRecord> warpToAttendanceMap(String data) {
        Map<Integer, AttendanceDRecord> map = new HashMap<Integer, AttendanceDRecord>();

        for (String dayString : data.split(";")) {
            int index = dayString.indexOf(":");
            String stringday = dayString.substring(0, index);
            int day = Integer.parseInt(stringday);

            String types = dayString.substring(index + 1).replace(",", "");

            char[] characterValue = types.toCharArray();

            AttendanceType morning = AttendanceType.forValue(characterValue[0]);
            AttendanceType afternoon = AttendanceType.forValue(characterValue[1]);
            AttendanceType evening = AttendanceType.forValue(characterValue[2]);

            AttendanceDRecord dayRecord = new AttendanceDRecord();
            dayRecord.setDay(day);
            dayRecord.setStyleClass(AttendanceMAdmin.calendarViewStyleClass[1]);
            dayRecord.setMorning(morning);
            dayRecord.setAfternoon(afternoon);
            dayRecord.setEvening(evening);
            map.put(day, dayRecord);
        }

        return map;
    }

    public static String warpToAttendanceString(Map<Integer, AttendanceDRecord> map) {
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

    public void setRecords(String data) {
        records = warpToAttendanceMap(data);
        attendances = new ArrayList<AttendanceDRecord>(records.values());
        providerArgs = ExtractProviderArgs(records);
    }

    public int getTmpId() {
        return tmpId;
    }

    public void setTmpId(int tmpId) {
        this.tmpId = tmpId;
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
        return records.get(day);
    }

    public List<AttendanceDRecord> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDRecord> attendances) {
        this.attendances = attendances;
    }

    public Integer[] getArguments() {
        return providerArgs;
    }

    public List<List<AttendanceDRecord>> getMonthView() {
        int yearMonth = SalaryDateUtil.convertToYearMonth(new Date());
        this.monthView = calendarView(yearMonth, records);
        return monthView;
    }

    public void setMonthView(List<List<AttendanceDRecord>> monthView) {
        this.monthView = monthView;
    }

    public String getAttendanceData() {
        return attendanceData;
    }

    public void setAttendanceData(String attendanceData) {
        this.attendanceData = attendanceData;
    }

}
