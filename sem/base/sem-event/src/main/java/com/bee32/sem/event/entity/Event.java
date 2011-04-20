package com.bee32.sem.event.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EVT")
public class Event
        extends EnterpriseEvent
        implements IEvent {

    private static final long serialVersionUID = 1L;

}
