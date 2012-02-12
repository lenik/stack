package com.bee32.sem.base.tx;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 事务性实体所具有的特性：
 *
 * <ul>
 * <li>事务与事件的集成
 * <li>事务的一般属性：标题、制单人、起始/终止时间、简短标题、备注信息等。
 */
@MappedSuperclass
public class TxEntity
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int SERIAL_LENGTH = 30;

    String serial;

    @Override
    public void populate(Object source) {
        if (source instanceof TxEntity)
            _populate((TxEntity) source);
        else
            super.populate(source);
    }

    protected void _populate(TxEntity o) {
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
