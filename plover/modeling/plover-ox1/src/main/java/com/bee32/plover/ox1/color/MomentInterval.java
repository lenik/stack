package com.bee32.plover.ox1.color;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

@MappedSuperclass
@Pink
public abstract class MomentInterval
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date beginTime = new Date();
    Date endTime;

    public MomentInterval() {
        super();
    }

    public MomentInterval(String name) {
        super(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof MomentInterval)
            _populate((MomentInterval) source);
        else
            super.populate(source);
    }

    protected void _populate(MomentInterval o) {
        super._populate(o);
        beginTime = o.beginTime;
        endTime = o.endTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Column(nullable = false)
    @Index(name = "##_beginTime")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "##_endTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
