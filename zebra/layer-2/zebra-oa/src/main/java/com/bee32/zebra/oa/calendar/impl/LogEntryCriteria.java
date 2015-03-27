package com.bee32.zebra.oa.calendar.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import net.bodz.bas.err.ParseException;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.tinylily.model.base.CoMomentIntervalCriteria;
import com.tinylily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.oa.calendar.LogEntry
 */
public class LogEntryCriteria
        extends CoMomentIntervalCriteria {

    String source;
    Integer opId;
    Integer formId;
    Integer categoryId;
    // String message;

    public boolean noOp;
    public boolean noForm;
    public boolean noCategory;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    static Map<Class<?>, String> classCategory;
    static {
        classCategory = new HashMap<Class<?>, String>();
        classCategory.put(AccountingEvent.class, "ACDOC");
    }

    public void setSourceClass(Class<?> clazz) {
        Table aTable = clazz.getAnnotation(Table.class);
        String tableName = aTable.name();
        this.source = tableName;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isNoOp() {
        return noOp;
    }

    public void setNoOp(boolean noOp) {
        this.noOp = noOp;
    }

    public boolean isNoForm() {
        return noForm;
    }

    public void setNoForm(boolean noForm) {
        this.noForm = noForm;
    }

    public boolean isNoCategory() {
        return noCategory;
    }

    public void setNoCategory(boolean noCategory) {
        this.noCategory = noCategory;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        source = map.getString("source", source);
        opId = map.getInt("op", opId);
        formId = map.getInt("form", formId);
        categoryId = map.getInt("cat", categoryId);
        noForm = map.getBoolean("no-form");
        noOp = map.getBoolean("no-op");
        noCategory = map.getBoolean("no-cat");
    }

}
