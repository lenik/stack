package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class OrganizationCriteria
        extends CoEntityCriteria {

    IntRange sizeRange;
    IntRange staffRange;
    boolean shipper;

    public IntRange getSizeRange() {
        return sizeRange;
    }

    public void setSizeRange(IntRange sizeRange) {
        this.sizeRange = sizeRange;
    }

    public IntRange getStaffRange() {
        return staffRange;
    }

    public void setStaffRange(IntRange staffRange) {
        this.staffRange = staffRange;
    }

    public boolean isShipper() {
        return shipper;
    }

    public void setShipper(boolean shipper) {
        this.shipper = shipper;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        sizeRange = map.getIntRange("sizes", sizeRange);
        staffRange = map.getIntRange("staff", sizeRange);
        shipper = map.getBoolean("shipper", shipper);
    }

}
