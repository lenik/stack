package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;

import com.bee32.zebra.tk.sea.MyMask;

public class AccountMask
        extends MyMask {

    public String codePrefix;
    public Integer maxDepth;

    @Override
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        codePrefix = map.getString("code", codePrefix);
        maxDepth = map.getInt("depth", maxDepth);
    }

}
