package com.bee32.sem.event;

import java.util.Collection;

/**
 * Generic State
 *
 * <p lang="en">
 */
public class GenericState
        extends EventState<GenericState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.GENERIC);

    public GenericState(int index, String name) {
        super(_CLASS | index, name);
    }

    public static GenericState forName(String name) {
        return forName(GenericState.class, name);
    }

    public static Collection<GenericState> values() {
        return values(GenericState.class);
    }

    public static GenericState forValue(Integer value) {
        return forValue(GenericState.class, value);
    }

    public static GenericState forValue(int value) {
        return forValue(new Integer(value));
    }

    static GenericState _(int index, String name) {
        return new GenericState(index, name);
    }

    /**
     * 未知
     *
     * <p lang="en">
     * Unknown
     */
    public static final GenericState UNKNOWN = _(0, "unknown");

    /**
     * 运行中
     *
     * <p lang="en">
     * Running
     */
    public static final GenericState RUNNING = _(1, "running");

    /**
     * 挂起
     *
     * <p lang="en">
     * Suspended
     */
    public static final GenericState SUSPENDED = _(2, "suspended");

    /**
     * 已取消
     *
     * <p lang="en">
     * Canceled
     */
    public static final GenericState CANCELED = _(3, "canceled");

    /**
     * 已完成
     *
     * <p lang="en">
     * Done
     */
    public static final GenericState DONE = _(4, "done");

    /**
     * 失败
     *
     * <p lang="en">
     * Failed
     */
    public static final GenericState FAILED = _(5, "failed");

    /**
     * 错误
     *
     * <p lang="en">
     * Errored
     */
    public static final GenericState ERRORED = _(6, "errored");

}
