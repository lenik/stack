package com.bee32.sem.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortDictEntity;
import com.bee32.sem.event.EventState;

/**
 * 事件状态
 */
@Entity
public class EventStatus
        extends ShortDictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private int flagsMask;
    private boolean closed;
    private EventState state;

    public EventStatus() {
        super();
    }

    public EventStatus(String name, String displayName, EventState state) {
        super(name, displayName);
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
        if (state == null)
            return 0;
        else
            return state.getIndex();
    }

    public void setState(int state) {
        this.state = EventState.get(state);
    }

}
