package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 主动产生的事件。
 */
@Entity
@DiscriminatorValue("ACT")
public class Activity
        extends Event
        implements IActivity {

    private static final long serialVersionUID = 1L;

}
