package com.bee32.sem.salary.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SalaryDictsBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    List<AttendanceType> attendanceTypes;

    List<SalaryElementDefDto> defs;

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

    public SelectableList<SalaryElementDefDto> getDefs() {
        if (defs == null) {
            synchronized (this) {
                if (defs == null) {
                    defs = mrefList(SalaryElementDef.class, SalaryElementDefDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(defs);
    }

    public SalaryElementDefDto getDef(int id) {
        for(SalaryElementDefDto  def : getDefs()){
            if(def.getId() == id)
                return def;
        }
        return null;
    }
}
