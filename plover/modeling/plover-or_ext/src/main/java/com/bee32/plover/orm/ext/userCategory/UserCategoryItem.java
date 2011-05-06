package com.bee32.plover.orm.ext.userCategory;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class UserCategoryItem
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    UserCategory category;

    Long intVal;
    Double floatVal;
    String textVal;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public UserCategory getCategory() {
        return category;
    }

    public void setCategory(UserCategory category) {
        this.category = category;
    }

    public Long getIntVal() {
        return intVal;
    }

    public void setIntVal(Long intVal) {
        this.intVal = intVal;
    }

    public Double getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(Double floatVal) {
        this.floatVal = floatVal;
    }

    public String getTextVal() {
        return textVal;
    }

    public void setTextVal(String textVal) {
        this.textVal = textVal;
    }

}
