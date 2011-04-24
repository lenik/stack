package com.bee32.sem.event;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventState;

public class SEMEventSamples
        extends EntitySamplesContribution {

    public static final EventPriority URGENT = new EventPriority("urgent", "紧急", 1);
    public static final EventPriority HIGH = new EventPriority("high", "高", 3);
    public static final EventPriority NORMAL = new EventPriority("normal", "普通", 5);
    public static final EventPriority LOW = new EventPriority("low", "低", 10);

    public static final EventState UNKNOWN = new EventState("unknown", "无状态");
    public static final EventState RUNNING = new EventState("running", "进行中");
    public static final EventState SUSPENDED = new EventState("suspended", "挂起");
    public static final EventState CANCELED = new EventState("canceled", "取消");
    public static final EventState DONE = new EventState("done", "成功");
    public static final EventState FAILED = new EventState("failed", "失败");
    public static final EventState ERRORED = new EventState("errored", "异常");

    @Override
    protected void preamble() {
        addNormalSample(URGENT, HIGH, NORMAL, LOW);
        addNormalSample(UNKNOWN, RUNNING, SUSPENDED, CANCELED, DONE, FAILED, ERRORED);
    }

}
