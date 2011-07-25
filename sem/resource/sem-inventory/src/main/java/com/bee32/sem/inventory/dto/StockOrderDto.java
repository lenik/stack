package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;

public class StockOrderDto
        extends StockItemListDto<StockOrder> {

    private static final long serialVersionUID = 1L;

    StockPeriodDto base;
    StockPeriodDto spec;
    StockOrderSubject subject;
    String serial;
    Long jobId;

    @Override
    protected void _marshal(StockOrder source) {
        base = mref(StockPeriodDto.class, source.getBase());
        spec = mref(StockPeriodDto.class, source.getSpec());
        subject = source.getSubject();
        serial = source.getSerial();
        jobId = source.getJobId();
    }

    @Override
    protected void _unmarshalTo(StockOrder target) {
        merge(target, "base", base);
        merge(target, "spec", spec);
        target.setSubject(subject);
        target.setSerial(serial);
        target.setJobId(jobId);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
