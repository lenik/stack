package com.bee32.sem.event;

import javax.persistence.Entity;

@Entity
public class Activity
        extends EnterpriseEvent
        implements IActivity {

    private static final long serialVersionUID = 1L;

}
