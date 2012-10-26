package com.bee32.sem.pricing.entity;

import java.beans.Transient;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 电机类型
 *
 * 风机所使用的电机类型。
 *
 */
@Entity
@Blue
public class MotorType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, MotorType> motorTypes = new TreeMap<Integer, MotorType>();

    public MotorType() {
        super();
    }

    public MotorType(int order, String name, String label) {
        super(order, name, label);
    }

    public MotorType(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        motorTypes.put(getOrder(), this);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof MotorType)
            _populate((MotorType) source);
        else
            super.populate(source);
    }

    protected void _populate(MotorType o) {
        super._populate(o);
    }

    @Transient
    public MotorType getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, MotorType> entry = motorTypes.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

}
