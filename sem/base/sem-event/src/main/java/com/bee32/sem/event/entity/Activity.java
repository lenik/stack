package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ACT")
public class Activity
        extends EnterpriseEvent
        implements IActivity {

    private static final long serialVersionUID = 1L;

}
