package com.bee32.zebra.tk.stat.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoMomentIntervalMask;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.tk.stat.MonthTrends
 */
public class MonthTrendsMask
        extends CoMomentIntervalMask {

    String table;
    String field;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        table = map.getString("table", table);
        field = map.getString("field", field);
    }

}
