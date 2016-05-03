package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;

import com.bee32.zebra.tk.sea.MyMask;

public class UOMMask
        extends MyMask {

    public String property;
    public boolean noProperty;

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        property = map.getString("property", property);
        noProperty = map.getBoolean("no-property");
    }

}
