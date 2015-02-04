package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.tk.sea.MyCriteria;
import com.tinylily.model.sea.QVariantMap;

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
