package com.bee32.sem.event;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventStatus;

public class SEMEventSamples
        extends EntitySamplesContribution {

    public static final EventPriority URGENT = new EventPriority("urgent", "紧急", 1);
    public static final EventPriority HIGH = new EventPriority("high", "高", 3);
    public static final EventPriority NORMAL = new EventPriority("normal", "普通", 5);
    public static final EventPriority LOW = new EventPriority("low", "低", 10);

    public static final EventStatus UNKNOWN = new EventStatus("unknown", "无状态", EventState.UNKNOWN);
    public static final EventStatus RUNNING = new EventStatus("running", "进行中", EventState.RUNNING);
    public static final EventStatus SUSPENDED = new EventStatus("suspended", "挂起", EventState.SUSPENDED);
    public static final EventStatus CANCELED = new EventStatus("canceled", "取消", EventState.CANCELED);
    public static final EventStatus DONE = new EventStatus("done", "成功", EventState.DONE);
    public static final EventStatus FAILED = new EventStatus("failed", "失败", EventState.FAILED);
    public static final EventStatus ERRORED = new EventStatus("errored", "异常", EventState.ERRORED);

    @Override
    protected void preamble() {
        addNormalSample(URGENT, HIGH, NORMAL, LOW);
        addNormalSample(UNKNOWN, RUNNING, SUSPENDED, CANCELED, DONE, FAILED, ERRORED);
    }

}
