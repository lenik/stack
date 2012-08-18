package com.bee32.sem.salary.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.entity.BaseBonus;

public class SalaryDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<BaseBonusDto> bonuses;
    List<AttendanceType> attendanceTypes;

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

    public SelectableList<AttendanceType> getAttendanceTypes() {
        if (attendanceTypes == null) {
            synchronized (this) {
                if (attendanceTypes == null) {
                    SiteInstance siteInstance = ThreadHttpContext.getSiteInstance();
                }
            }
        }
        return SelectableList.decorate(attendanceTypes);
    }

}
