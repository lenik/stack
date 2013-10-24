package com.bee32.sem.chance.entity;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 机会阶段
 *
 * 描述机会当前的阶段，如：初始化，交涉中，合同签订。
 *
 * <p lang="en">
 * Chance Stage
 */
@Entity
@Blue
public class ChancePriority
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, ChancePriority> stages = new TreeMap<Integer, ChancePriority>();

    public ChancePriority() {
        super();
    }

    public ChancePriority(int order, String name, String label) {
        super(order, name, label);
    }

    public ChancePriority(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        stages.put(getOrder(), this);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof ChancePriority)
            _populate((ChancePriority) source);
        else
            super.populate(source);
    }

    protected void _populate(ChancePriority o) {
        super._populate(o);
    }

    @Transient
    public ChancePriority getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, ChancePriority> entry = stages.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

}
