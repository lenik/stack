package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class AccountCriteria
        extends CoEntityCriteria {

    int code_;

    public int getCodePrefix() {
        return code_;
    }

    public void setCodePrefix(int code) {
        this.code_ = code;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        code_ = map.getInt("code", code_);
    }

}
