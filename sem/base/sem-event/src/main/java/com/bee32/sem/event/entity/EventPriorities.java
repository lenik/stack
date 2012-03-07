package com.bee32.sem.event.entity;

import static com.bee32.sem.event.entity.EventPriority.P_HIGH;
import static com.bee32.sem.event.entity.EventPriority.P_LOW;
import static com.bee32.sem.event.entity.EventPriority.P_NORMAL;
import static com.bee32.sem.event.entity.EventPriority.P_URGENT;

import com.bee32.plover.orm.sample.StandardSamples;

public class EventPriorities
        extends StandardSamples {

    public final EventPriority URGENT = new EventPriority(P_URGENT, "urgent", "紧急");
    public final EventPriority HIGH = new EventPriority(P_HIGH, "high", "高");
    public final EventPriority NORMAL = new EventPriority(P_NORMAL, "normal", "普通");
    public final EventPriority LOW = new EventPriority(P_LOW, "low", "低");

}
