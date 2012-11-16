package com.bee32.xem.zjhf.entity;

import java.beans.Transient;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 阀类型
 */
@Entity
@Table(name = "zjhf_valve_type")
public class ValveType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, ValveType> valveTypes = new TreeMap<Integer, ValveType>();

    public ValveType() {
        super();
    }

    public ValveType(int order, String name, String label) {
        super(order, name, label);
    }

    public ValveType(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        valveTypes.put(getOrder(), this);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof ValveType)
            _populate((ValveType) source);
        else
            super.populate(source);
    }

    protected void _populate(ValveType o) {
        super._populate(o);
    }

    @Transient
    public ValveType getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, ValveType> entry = valveTypes.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

}
