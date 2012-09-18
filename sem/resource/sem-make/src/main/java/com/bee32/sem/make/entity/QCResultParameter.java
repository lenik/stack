package com.bee32.sem.make.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 质检项目和结果
 *
 * 包括质检的具本项目和具体质检值。
 *
 * @author jack
 *
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "qc_result_parameter_seq", allocationSize = 1)
public class QCResultParameter
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int VALUE_LENGTH = 200;

    QCResult parent;
    QCSpecParameter key;
    String value;

    /**
     * 质检结果
     *
     * 质检结果主控类。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public QCResult getParent() {
        return parent;
    }

    public void setParent(QCResult parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    /**
     * 质检标准
     *
     * 本质检结果对应的质检标准。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public QCSpecParameter getKey() {
        return key;
    }

    public void setKey(QCSpecParameter key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
    }

    /**
     * 质检值
     *
     * 具体的质检值。
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
