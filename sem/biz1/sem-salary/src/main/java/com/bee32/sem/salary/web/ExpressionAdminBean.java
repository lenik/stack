package com.bee32.sem.salary.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class ExpressionAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<SalaryElementDefDto> defs;

    public ExpressionAdminBean() {
        super(SalaryElementDef.class, SalaryElementDefDto.class, 0);
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
}
