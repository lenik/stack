package com.bee32.sem.event;

public class Task
        extends EnterpriseEvent
        implements ITask {

    private static final long serialVersionUID = 1L;

    private TaskPriority taskPriority;

    @Override
    public TaskPriority getPriority() {
        return taskPriority;
    }

    public void setPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

}
