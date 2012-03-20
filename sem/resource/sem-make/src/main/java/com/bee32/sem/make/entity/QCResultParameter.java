package com.bee32.sem.make.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "qc_result_parameter_seq", allocationSize = 1)
public class QCResultParameter
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int VALUE_LENGTH = 100;

    QCResult parent;
    QCSpecParameter key;
    String value;

    @ManyToOne(optional = false)
    public QCResult getParent() {
        return parent;
    }

    public void setParent(QCResult parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @ManyToOne(optional = false)
    public QCSpecParameter getKey() {
        return key;
    }

    public void setSpec(QCSpecParameter key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
    }

    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
