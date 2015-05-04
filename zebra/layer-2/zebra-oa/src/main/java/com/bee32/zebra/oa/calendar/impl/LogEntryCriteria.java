package com.bee32.zebra.oa.calendar.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoMomentIntervalCriteria;
import net.bodz.lily.model.sea.QVariantMap;

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

    public void setSourceClass(Class<?> clazz) {
        TableMetadataRegistry registry = TableMetadataRegistry.getInstance();
        TableMetadata metadata = registry.get(clazz);
        this.source = metadata.getName();
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
