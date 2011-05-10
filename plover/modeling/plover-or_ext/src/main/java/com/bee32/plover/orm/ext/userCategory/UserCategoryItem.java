package com.bee32.plover.orm.ext.userCategory;

import javax.free.IllegalUsageException;
import javax.free.UnexpectedException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.color.BlueEntity;

@Entity
public class UserCategoryItem
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    UserCategory category;

    Long intVal;
    Double floatVal;
    String textVal;

    String description;

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

    @Column(length = 100)
    public String getTextVal() {
        return textVal;
    }

    public void setTextVal(String textVal) {
        this.textVal = textVal;
    }

    @Transient
    public Object getValue() {
        if (category == null)
            throw new NullPointerException("category");

        switch (category.type) {
        case INTEGER:
            return intVal;

        case DECIMAL:
            return floatVal;

        case TEXT:
            return textVal;
        }
        throw new UnexpectedException();
    }

    public void setValue(int value) {
        setValue((long) value);
    }

    public void setValue(long value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserCategoryTypeEnum.INTEGER)
            throw new IllegalUsageException("Category is not of INTEGER type.");

        intVal = value;
    }

    public void setValue(float value) {
        setValue((double) value);
    }

    public void setValue(double value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserCategoryTypeEnum.DECIMAL)
            throw new IllegalUsageException("Category is not of DECIMAL type.");

        floatVal = value;
    }

    public void setValue(String value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserCategoryTypeEnum.TEXT)
            throw new IllegalUsageException("Category is not of TEXT type.");

        textVal = value;
    }

    @Column(length = 60)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
