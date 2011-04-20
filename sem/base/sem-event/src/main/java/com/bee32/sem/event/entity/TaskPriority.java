package com.bee32.sem.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class TaskPriority
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String displayName;
    private int priority;

    public TaskPriority(String name, int priority) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.priority = priority;
    }

    @NaturalId
    @Column(length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 20)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static final TaskPriority HIGH = new TaskPriority("high", -100);
    public static final TaskPriority NORMAL = new TaskPriority("normal", 0);
    public static final TaskPriority LOW = new TaskPriority("low", 100);

}
