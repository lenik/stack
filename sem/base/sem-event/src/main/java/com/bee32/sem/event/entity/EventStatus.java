package com.bee32.sem.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NumberDict;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.GenericState;

/**
 * 事件状态
 *
 * <p lang="en">
 */
@Entity
public class EventStatus
        extends NumberDict {

    private static final long serialVersionUID = 1L;

    private int flagsMask;
    private boolean closed;
    private EventState<?> state = GenericState.UNKNOWN;

    public EventStatus() {
        super();
    }

    public EventStatus(EventState<?> state, String alias, String description) {
        super(state.getValue(), alias, description);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof EventStatus)
            _populate((EventStatus) source);
        else
            super.populate(source);
    }

    protected void _populate(EventStatus o) {
        super._populate(o);
        flagsMask = o.flagsMask;
        closed = o.closed;
        state = o.state;
    }

    /**
     * 标识掩码
     *
     * <p lang="en">
     */
    @Column(nullable = false)
    public int getFlagsMask() {
        return flagsMask;
    }

    public void setFlagsMask(int flagsMask) {
        this.flagsMask = flagsMask;
    }

    /**
     * 已关闭
     *
     * <p lang="en">
     */
    @Column(nullable = false)
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}
