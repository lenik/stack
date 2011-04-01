package com.bee32.sem.event;

import javax.persistence.Entity;

@Entity
public class Event
        extends EnterpriseEvent
        implements IEvent {

    private static final long serialVersionUID = 1L;

}
