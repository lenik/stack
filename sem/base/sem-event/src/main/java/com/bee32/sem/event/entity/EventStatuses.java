package com.bee32.sem.event.entity;

import com.bee32.plover.orm.sample.StandardSamples;
import com.bee32.sem.event.GenericState;

/**
 * 预定义事件状态
 *
 * <p lang="en">
 * Event Statuses
 */
public class EventStatuses
        extends StandardSamples {

    /**
     * 未知
     *
     * <p lang="en">
     * Unknown
     */
    public final EventStatus UNKNOWN = new EventStatus(GenericState.UNKNOWN, "unknown", "无状态");

    /**
     * 运行中
     *
     * <p lang="en">
     * Running
     */
    public final EventStatus RUNNING = new EventStatus(GenericState.RUNNING, "running", "进行中");

    /**
     * 已挂起
     *
     * <p lang="en">
     * Suspended
     */
    public final EventStatus SUSPENDED = new EventStatus(GenericState.SUSPENDED, "suspended", "挂起");

    /**
     * 已取消
     *
     * <p lang="en">
     * Canceled
     */
    public final EventStatus CANCELED = new EventStatus(GenericState.CANCELED, "canceled", "取消");

    /**
     * 完成
     *
     * <p lang="en">
     * Done
     */
    public final EventStatus DONE = new EventStatus(GenericState.DONE, "done", "成功");

    /**
     * 失败
     *
     * <p lang="en">
     * Failed
     */
    public final EventStatus FAILED = new EventStatus(GenericState.FAILED, "failed", "失败");

    /**
     * 出错
     *
     * <p lang="en">
     * Errored
     */
    public final EventStatus ERRORED = new EventStatus(GenericState.ERRORED, "errored", "异常");

}
