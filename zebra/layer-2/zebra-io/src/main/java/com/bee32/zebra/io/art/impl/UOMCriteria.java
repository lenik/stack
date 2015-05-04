package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.sea.QVariantMap;

import com.bee32.zebra.tk.sea.MyCriteria;

public class UOMCriteria
        extends MyCriteria {

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
