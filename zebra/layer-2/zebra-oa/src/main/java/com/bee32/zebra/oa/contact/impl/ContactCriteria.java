package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class ContactCriteria
        extends CoEntityCriteria {

    public Integer orgId;
    public Integer orgUnitId;
    public Integer personId;
    public String usage;
    public String country;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        orgId = map.getInt("org", orgId);
        orgUnitId = map.getInt("ou", orgUnitId);
        personId = map.getInt("person", personId);
        usage = map.getString("usage", usage);
        country = map.getString("country", country);
    }

}
