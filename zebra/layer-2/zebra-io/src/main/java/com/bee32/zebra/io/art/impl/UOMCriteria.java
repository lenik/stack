package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class UOMCriteria
        extends CoEntityCriteria {

    public String property;
    public boolean noProperty;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        property = map.getString("property", property);
        noProperty = map.getBoolean("no-property");
    }

}
