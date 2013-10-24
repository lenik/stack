package com.bee32.sem.chance.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.dict.NumberDict;

/**
 * 机会优先级
 *
 * 描述机会的优先级，用于排序。
 *
 * <p lang="en">
 * Chance Priority
 */
@Entity
@Blue
public class ChancePriority
        extends NumberDict {

    private static final long serialVersionUID = 1L;

    public static final int P_URGENT = 10;
    public static final int P_HIGH = 30;
    public static final int P_NORMAL = 50;
    public static final int P_LOW = 100;

    public ChancePriority() {
    }

    public ChancePriority(int priority, String label, String description) {
        super(priority, label, description);
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
    public int getPriority() {
        return getNumber();
    }

    public void setPriority(int priority) {
        setNumber(priority);
    }

}
