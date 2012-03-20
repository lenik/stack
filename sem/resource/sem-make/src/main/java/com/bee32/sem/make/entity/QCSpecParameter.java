package com.bee32.sem.make.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 质量规范参数定义
 *
 * <ul>
 * <li>label = 参数名称
 * <li>description = 参数描述
 * </ul>
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "qc_spec_parameter_seq", allocationSize = 1)
public class QCSpecParameter
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int VALUE_LENGTH = 200;

    QCSpec parent;
    // String typeName;
    String value;
    boolean required;

    @ManyToOne(optional = false)
    public QCSpec getParent() {
        return parent;
    }

    public void setParent(QCSpec parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @Column(nullable = false)
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 质检标准值
     * @return
     */
    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
