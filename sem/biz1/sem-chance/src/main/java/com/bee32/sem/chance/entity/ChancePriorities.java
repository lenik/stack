package com.bee32.sem.chance.entity;

import static com.bee32.sem.chance.entity.ChancePriority.*;

import com.bee32.plover.orm.sample.StandardSamples;

public class ChancePriorities
        extends StandardSamples {

    /**
     * 紧急
     *
     * <p lang="en">
     * Urgent
     */
    public final ChancePriority URGENT = new ChancePriority(P_URGENT, "紧急", "紧急");

    /**
     * 高
     *
     * <p lang="en">
     * High
     */
    public final ChancePriority HIGH = new ChancePriority(P_HIGH, "高", "高");

    /**
     * 正常
     *
     * <p lang="en">
     * Normal
     */
    public final ChancePriority NORMAL = new ChancePriority(P_NORMAL, "普通", "普通");

    /**
     * 低
     *
     * <p lang="en">
     * Low
     */
    public final ChancePriority LOW = new ChancePriority(P_LOW, "低", "低");

}
