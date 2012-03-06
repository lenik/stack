package com.bee32.sem.event.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.dict.NumberDict;

@Entity
public class EventPriority
        extends NumberDict {

    private static final long serialVersionUID = 1L;

    public static final int P_URGENT = 10;
    public static final int P_HIGH = 30;
    public static final int P_NORMAL = 50;
    public static final int P_LOW = 100;

    public EventPriority() {
    }

    public EventPriority(int priority, String alias, String description) {
        super(priority, alias, description);
    }

X-Population

    @Transient
    public int getPriority() {
        return getNumber();
    }

    public void setPriority(int priority) {
        setNumber(priority);
    }

}
