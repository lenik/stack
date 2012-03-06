package com.bee32.plover.ox1.userMode;

import javax.free.IllegalUsageException;
import javax.free.UnexpectedException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "user_category_item_seq", allocationSize = 1)
public class UserCategoryItem
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    UserCategory category;

    Long intVal;
    Double doubleVal;
    String textVal;

    String description;

X-Population

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
        return doubleVal;
    }

    public void setFloatVal(Double doubleVal) {
        this.doubleVal = doubleVal;
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

        if (category.type == UserDataType.INTEGER)
            return intVal;

        else if (category.type == UserDataType.DOUBLE)
            return doubleVal;

        else if (category.type == UserDataType.TEXT)
            return textVal;

        else
            throw new UnexpectedException();
    }

    public void setValue(int value) {
        setValue((long) value);
    }

    public void setValue(long value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserDataType.INTEGER)
            throw new IllegalUsageException("Category is not of INTEGER type.");

        intVal = value;
    }

    public void setValue(double value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserDataType.DOUBLE)
            throw new IllegalUsageException("Category is not of DECIMAL type.");

        doubleVal = value;
    }

    public void setValue(String value) {
        if (category == null)
            throw new NullPointerException("category");

        if (category.type != UserDataType.TEXT)
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
