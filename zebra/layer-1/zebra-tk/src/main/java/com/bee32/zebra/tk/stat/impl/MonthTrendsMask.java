package com.bee32.zebra.tk.stat.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.base.CoMomentIntervalMask;

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
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        table = map.getString("table", table);
        field = map.getString("field", field);
    }

}
