package com.bee32.sem.pricing.entity;

import java.beans.Transient;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 风机类型
 *
 */
@Entity
@Blue
public class AirBlowerType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, AirBlowerType> airBlowerTypes = new TreeMap<Integer, AirBlowerType>();

    public AirBlowerType() {
        super();
    }

    public AirBlowerType(int order, String name, String label) {
        super(order, name, label);
    }

    public AirBlowerType(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        airBlowerTypes.put(getOrder(), this);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AirBlowerType)
            _populate((AirBlowerType) source);
        else
            super.populate(source);
    }

    protected void _populate(AirBlowerType o) {
        super._populate(o);
    }

    @Transient
    public AirBlowerType getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, AirBlowerType> entry = airBlowerTypes.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

}
