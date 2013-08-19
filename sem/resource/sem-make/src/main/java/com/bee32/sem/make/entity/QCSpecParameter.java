package com.bee32.sem.make.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 质检标准名细
 *
 * 质量规范参数定义。
 *
 * <ul>
 * <li>label = 质检内容
 * <li>value = 标准值
 * <li>description = 标准描述
 * </ul>
 *
 *
 * <p lang="en">
 * QC Spec Parameter
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "qc_spec_parameter_seq", allocationSize = 1)
public class QCSpecParameter
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int VALUE_LENGTH = 200;

    QCSpec parent;
    String value;
    boolean required;

    /**
     * 质检标准
     *
     * 质检标准主控类。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public QCSpec getParent() {
        return parent;
    }

    public void setParent(QCSpec parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    /**
     * 专检否
     *
     * 本质检项目是否必须进行检测。
     *
     * @return
     */
    @Column(nullable = false)
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 质检标准值
     *
     * 预先定义的质检标准值。
     *
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
