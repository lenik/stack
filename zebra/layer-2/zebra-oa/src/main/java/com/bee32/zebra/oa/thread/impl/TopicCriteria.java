package com.bee32.zebra.oa.thread.impl;

import java.util.Set;

import net.bodz.bas.t.set.IntRange;

import com.tinylily.model.mx.base.CoMessageCriteria;

public class TopicCriteria
        extends CoMessageCriteria {

    /**
     * 分类
     */
    Integer categoryId;

    /**
     * 阶段
     */
    Integer phaseId;

    /**
     * 价值
     */
    IntRange valueRange;

    /**
     * 标签
     */
    Set<String> tags;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public IntRange getValueRange() {
        return valueRange;
    }

    public void setValueRange(IntRange valueRange) {
        this.valueRange = valueRange;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
