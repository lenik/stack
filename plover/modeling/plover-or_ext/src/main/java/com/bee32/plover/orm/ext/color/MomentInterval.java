package com.bee32.plover.orm.ext.color;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@Pink
public abstract class MomentInterval
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Date beginTime;
    Date endTime;

    public MomentInterval() {
        super();

    }

    public MomentInterval(String name) {
        super(name);
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
