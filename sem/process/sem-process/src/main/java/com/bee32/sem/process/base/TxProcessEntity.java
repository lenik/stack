package com.bee32.sem.process.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

@MappedSuperclass
public abstract class TxProcessEntity
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    public static final int SERIAL_LENGTH = 30;

    String serial;

    @Override
    public void populate(Object source) {
        if (source instanceof TxProcessEntity)
            _populate((TxProcessEntity) source);
        else
            super.populate(source);
    }

    protected void _populate(TxProcessEntity o) {
        super._populate(o);
        serial = o.serial;
    }

    /**
     * 单据序列号。Serial ID, or Second ID.
     */
    @NaturalId
    @Column(length = SERIAL_LENGTH)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    protected final Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

    @Override
    protected final ICriteriaElement selector(String prefix) {
        if (serial == null)
            return null;
        else
            return new Equals(prefix + "serial", serial);
    }

}
