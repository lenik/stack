package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class OrgUnitCriteria
        extends CoEntityCriteria {

    Integer orgId;
    IntRange staffCountRange;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public IntRange getStaffCountRange() {
        return staffCountRange;
    }

    public void setStaffCountRange(IntRange staffCountRange) {
        this.staffCountRange = staffCountRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        orgId = map.getInt("org", orgId);
        staffCountRange = map.getIntRange("staff", staffCountRange);
    }

}
