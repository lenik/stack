package com.bee32.plover.ox1.color;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
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

    /**
     * 起始时间
     *
     * 反应记录对应事件的发生时刻。
     */
    @Index(name = "##_beginTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 终止时间
     *
     * 反应记录对应事件的结束时刻。
     */
    @Index(name = "##_endTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
