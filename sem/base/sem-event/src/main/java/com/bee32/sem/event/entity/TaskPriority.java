package com.bee32.sem.event.entity;

public class TaskPriority {

    private String name;
    private int priority;

    public TaskPriority(String name, int priority) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
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
