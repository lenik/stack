package com.bee32.sem.wage.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.attendance.dto.AttendanceTypeDto;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.wage.dto.BaseBonusDto;
import com.bee32.sem.wage.entity.BaseBonus;

public class WageDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<BaseBonusDto> bonuses;

    List<AttendanceTypeDto> attendanceTypes;

    List<AttendanceTypeDto> absentTypes;

    public SelectableList<BaseBonusDto> getBonuses() {
        if (bonuses == null) {
            synchronized (this) {
                if (bonuses == null) {
                    bonuses = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(bonuses);
    }

    public SelectableList<AttendanceTypeDto> getAttendanceTypes() {
        if (attendanceTypes == null) {
            synchronized (this) {
                if (attendanceTypes == null) {
                    attendanceTypes = mrefList(AttendanceType.class, AttendanceTypeDto.class, 0,
                            AttendanceCriteria.getAttType(true));
                }
            }
        }
        return SelectableList.decorate(attendanceTypes);
    }

    public SelectableList<AttendanceTypeDto> getAbsentTypes() {
        if (absentTypes == null)
            synchronized (this) {
                if (absentTypes == null) {
                    absentTypes = mrefList(AttendanceType.class, AttendanceTypeDto.class, 0,
                            AttendanceCriteria.getAttType(false));
                }
            }
        return SelectableList.decorate(absentTypes);
    }

}
