package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.tk.sea.MyCriteria;
import com.tinylily.model.sea.QVariantMap;

public class AccountCriteria
        extends MyCriteria {

    public String codePrefix;
    public Integer maxDepth;

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        codePrefix = map.getString("code", codePrefix);
        maxDepth = map.getInt("depth", maxDepth);
    }

}
