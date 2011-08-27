package com.bee32.sem.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NumberDict;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.GenericState;

/**
 * 事件状态
 */
@Entity
public class EventStatus
        extends NumberDict {

    private static final long serialVersionUID = 1L;

    private int flagsMask;
    private boolean closed;
    private EventState state = GenericState.UNKNOWN;

    public EventStatus() {
        super();
    }

    public EventStatus(EventState state, String alias, String description) {
        super(state.getValue(), alias, description);
    }

    @Column(nullable = false)
    public int getFlagsMask() {
        return flagsMask;
    }

    public void setFlagsMask(int flagsMask) {
        this.flagsMask = flagsMask;
    }

    @Column(nullable = false)
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Column(nullable = false)
    public int getState() {
        return state.getValue();
    }

    public void setState(int state) {
        this.state = EventState.valueOf(state);
    }

    public static final EventStatus UNKNOWN = new EventStatus(GenericState.UNKNOWN, "unknown", "无状态");
    public static final EventStatus RUNNING = new EventStatus(GenericState.RUNNING, "running", "进行中");
    public static final EventStatus SUSPENDED = new EventStatus(GenericState.SUSPENDED, "suspended", "挂起");
    public static final EventStatus CANCELED = new EventStatus(GenericState.CANCELED, "canceled", "取消");
    public static final EventStatus DONE = new EventStatus(GenericState.DONE, "done", "成功");
    public static final EventStatus FAILED = new EventStatus(GenericState.FAILED, "failed", "失败");
    public static final EventStatus ERRORED = new EventStatus(GenericState.ERRORED, "errored", "异常");

}
