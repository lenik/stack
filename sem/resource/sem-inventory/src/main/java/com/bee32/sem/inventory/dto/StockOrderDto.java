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

    StockWarehouseDto warehouse;

    @Override
    protected void _marshal(StockOrder source) {
        base = mref(StockPeriodDto.class, source.getBase());
        spec = mref(StockPeriodDto.class, source.getSpec());
        subject = source.getSubject();
        serial = source.getSerial();
        jobId = source.getJobId();
        warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
    }

    @Override
    protected void _unmarshalTo(StockOrder target) {
        merge(target, "base", base);
        merge(target, "spec", spec);
        target.setSubject(subject);
        target.setSerial(serial);
        target.setJobId(jobId);
        merge(target, "warehouse", warehouse);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public StockPeriodDto getBase() {
        return base;
    }

    public void setBase(StockPeriodDto base) {
        if (base == null)
            throw new NullPointerException("base");
        this.base = base;
    }

    public StockPeriodDto getSpec() {
        return spec;
    }

    public void setSpec(StockPeriodDto spec) {
        this.spec = spec;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

}
