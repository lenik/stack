package com.bee32.zebra.oa.calendar.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DateRange;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.tinylily.model.base.CoObjectCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.oa.calendar.LogEntry
 */
public class LogEntryCriteria
        extends CoObjectCriteria {

    String category;
    Integer opId;
    // String message;
    DateRange dateRange;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    static Map<Class<?>, String> classCategory;
    static {
        classCategory = new HashMap<Class<?>, String>();
        classCategory.put(AccountingEvent.class, "ACDOC");
    }

    public void setCategory(Class<?> clazz) {
        Table aTable = clazz.getAnnotation(Table.class);
        String tableName = aTable.name();
        this.category = tableName;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
    }

}
