package com.bee32.sem.event.entity;

import static com.bee32.sem.event.entity.EventPriority.*;

import com.bee32.plover.orm.sample.StandardSamples;

public class EventPriorities
        extends StandardSamples {

    /**
     * 紧急
     *
     * <p lang="en">
     * Urgent
     */
    public final EventPriority URGENT = new EventPriority(P_URGENT, "urgent", "紧急");

    /**
     * 高
     *
     * <p lang="en">
     * High
     */
    public final EventPriority HIGH = new EventPriority(P_HIGH, "high", "高");

    /**
     * 正常
     *
     * <p lang="en">
     * Normal
     */
    public final EventPriority NORMAL = new EventPriority(P_NORMAL, "normal", "普通");

    /**
     * 低
     *
     * <p lang="en">
     * Low
     */
    public final EventPriority LOW = new EventPriority(P_LOW, "low", "低");

}
