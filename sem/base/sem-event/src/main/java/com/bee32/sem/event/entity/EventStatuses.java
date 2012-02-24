package com.bee32.sem.event.entity;

import com.bee32.plover.orm.util.StandardSamples;
import com.bee32.sem.event.GenericState;

public class EventStatuses
        extends StandardSamples {

    public final EventStatus UNKNOWN = new EventStatus(GenericState.UNKNOWN, "unknown", "无状态");
    public final EventStatus RUNNING = new EventStatus(GenericState.RUNNING, "running", "进行中");
    public final EventStatus SUSPENDED = new EventStatus(GenericState.SUSPENDED, "suspended", "挂起");
    public final EventStatus CANCELED = new EventStatus(GenericState.CANCELED, "canceled", "取消");
    public final EventStatus DONE = new EventStatus(GenericState.DONE, "done", "成功");
    public final EventStatus FAILED = new EventStatus(GenericState.FAILED, "failed", "失败");
    public final EventStatus ERRORED = new EventStatus(GenericState.ERRORED, "errored", "异常");

}
