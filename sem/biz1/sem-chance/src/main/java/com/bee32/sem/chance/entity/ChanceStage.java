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
 */
@Entity
@Blue
public class ChanceStage
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, ChanceStage> stages = new TreeMap<Integer, ChanceStage>();

    public ChanceStage() {
        super();
    }

    public ChanceStage(int order, String name, String label) {
        super(order, name, label);
    }

    public ChanceStage(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        stages.put(getOrder(), this);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof ChanceStage)
            _populate((ChanceStage) source);
        else
            super.populate(source);
    }

    protected void _populate(ChanceStage o) {
        super._populate(o);
    }

    @Transient
    public ChanceStage getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, ChanceStage> entry = stages.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

}
