package com.bee32.sem.salary.util;

import com.bee32.sem.attendance.entity.AttendanceMRecord;

public class AttendanceTest {
    public static void main(String args[]) {
        String test = "1:A,B,C;2:D,E,F";
        // todo
        AttendanceMRecord attendance = new AttendanceMRecord();
        attendance.setYearMonth(201210);
        attendance.setSafe(true);
        attendance.setAttendanceData(test);

// AttendanceMRecordDto dto = DTOs.marshal(AttendanceMRecordDto.class, attendance);

        for (String s : test.split(";")) {
            String temp = s.substring(2).replace(",", "");
            System.out.println("tempString=" + temp);
            char[] c = temp.toCharArray();
            for (int i = 0; i < c.length; i++) {

            }
        }

    }
}
