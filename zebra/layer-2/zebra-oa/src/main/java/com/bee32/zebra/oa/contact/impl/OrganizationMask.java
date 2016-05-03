package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.QVariantMap;

public class OrganizationMask
        extends PartyMask {

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
    public void readObject(IVariantMap<String> _map)
            throws ParseException {
        super.readObject(_map);
        QVariantMap<String> map = QVariantMap.from(_map);
        sizeRange = map.getIntRange("sizes", sizeRange);
        staffRange = map.getIntRange("staff", sizeRange);
        shipper = map.getBoolean("shipper", shipper);
    }

}
