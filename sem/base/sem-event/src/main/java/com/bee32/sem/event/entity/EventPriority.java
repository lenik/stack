package com.bee32.sem.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.ext.dict.ShortDictEntity;

@Entity
public class EventPriority
        extends ShortDictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private int priority;

    public EventPriority() {
    }

    public EventPriority(String name, String displayName, int priority) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.displayName = displayName;
        this.priority = priority;
    }

    @NaturalId
    @Column(nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
