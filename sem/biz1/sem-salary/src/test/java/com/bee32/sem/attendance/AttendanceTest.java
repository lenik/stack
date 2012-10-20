package com.bee32.sem.attendance;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.attendance.dto.AttendanceMRecordDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.util.AttendanceDRecord;

public class AttendanceTest {
    public static void main(String args[]) {
        String test = "1:A,B,C;2:D,E,F";
        // todo
        AttendanceMRecord attendance = new AttendanceMRecord();
        attendance.setYearMonth(201210);
        attendance.setSafe(true);
        attendance.setAttendanceData(test);

        AttendanceMRecordDto dto = DTOs.marshal(AttendanceMRecordDto.class, attendance);

        AttendanceDRecord day = dto.getRecord(1);

// for (String s : test.split(";")) {
// String temp = s.substring(2).replace(",", "");
// System.out.println("tempString=" + temp);
// char[] c = temp.toCharArray();
// for (int i = 0; i < c.length; i++) {
// System.out.println(c[i]);
// AttendanceType a = AttendanceType.forValue(c[i]);
// System.out.println("^^^^^^^^^^^^^^^^^");
// System.out.println(a.getName());
// }
// }

    }
}
