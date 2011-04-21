package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 用户计划。
 */
@Entity
@DiscriminatorValue("TSK")
public class Task
        extends Event
        implements ITask {

    private static final long serialVersionUID = 1L;

}
