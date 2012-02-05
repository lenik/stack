package com.bee32.plover.ox1.color;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;

public abstract class MomentIntervalDto<E extends MomentInterval>
        extends UIEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    protected Date beginTime;
    protected Date endTime;

    public MomentIntervalDto() {
        super();
    }

    public MomentIntervalDto(int fmask) {
        super(fmask);
    }

    @Override
    public MomentIntervalDto<E> populate(Object source) {
        if (source instanceof MomentIntervalDto<?>) {
            MomentIntervalDto<?> o = (MomentIntervalDto<?>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(MomentIntervalDto<?> o) {
        super._populate(o);
        beginTime = o.beginTime;
        endTime = o.endTime;
    }

    protected void __marshal(E source) {
        super.__marshal(source);
        beginTime = source.getBeginTime();
        endTime = source.getEndTime();
    }

    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        beginTime = map.getDate("beginTime");
        endTime = map.getDate("endTime");
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
