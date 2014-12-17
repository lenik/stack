package com.bee32.zebra.oa.thread.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DateRange;
import net.bodz.bas.t.range.IntRange;

import com.tinylily.model.mx.base.CoMessageCriteria;
import com.tinylily.model.sea.QVariantMap;

public class TopicCriteria
        extends CoMessageCriteria {

    Integer categoryId;
    Integer phaseId;
    DateRange dateRange;
    IntRange valueRange;
    Set<String> tags;

    /**
     * 分类
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 阶段
     */
    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * 价值
     */
    public IntRange getValueRange() {
        return valueRange;
    }

    public void setValueRange(IntRange valueRange) {
        this.valueRange = valueRange;
    }

    /**
     * 标签
     */
    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        categoryId = map.getInt("cat", categoryId);
        phaseId = map.getInt("phase", phaseId);
        dateRange = map.getDateRange("dates", dateRange);
        valueRange = map.getIntRange("values", valueRange);
        String tagsStr = map.getString("tags");
        if (tagsStr != null)
            tags = new TreeSet<String>(Arrays.asList(tagsStr.split(",")));
    }

}
