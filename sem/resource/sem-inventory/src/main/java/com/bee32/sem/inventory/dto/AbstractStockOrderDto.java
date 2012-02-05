package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderVerifySupportDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class AbstractStockOrderDto<E extends StockOrder>
        extends StockItemListDto<E>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    StockPeriodDto base;
    StockPeriodDto spec;
    StockOrderSubject subject;
    Long jobId;

    OrgDto org;
    OrgUnitDto orgUnit;

    StockWarehouseDto warehouse;

    StockOrderVerifySupportDto stockOrderVerifySupport;

    public AbstractStockOrderDto() {
        super();
    }

    public AbstractStockOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    public AbstractStockOrderDto<E> populate(Object source) {
        if (source instanceof StockOrderDto) {
            StockOrderDto o = (StockOrderDto) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(AbstractStockOrderDto<E> o) {
        super._populate(o);
        base = o.base;
        spec = o.spec;
        subject = o.subject;
        jobId = o.jobId;
        org = o.org;
        orgUnit = o.orgUnit;
        warehouse = o.warehouse;
    }

    @Override
    protected void _marshal(E source) {
        base = mref(StockPeriodDto.class, source.getBase());
        spec = mref(StockPeriodDto.class, source.getSpec());
        subject = source.getSubject();
        jobId = source.getJobId();
        org = mref(OrgDto.class, source.getOrg());
        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
        stockOrderVerifySupport = marshal(StockOrderVerifySupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(E target) {
        merge(target, "base", base);
        merge(target, "spec", spec);
        target.setSubject(subject);
        target.setJobId(jobId);
        merge(target, "org", org);
        merge(target, "orgUnit", orgUnit);
        merge(target, "warehouse", warehouse);
        merge(target, "verifyContext", stockOrderVerifySupport);
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

    @Override
    public StockOrderVerifySupportDto getVerifyContext() {
        return stockOrderVerifySupport;
    }

    public void setVerifyContext(StockOrderVerifySupportDto stockOrderVerifySupport) {
        this.stockOrderVerifySupport = stockOrderVerifySupport;
    }

}
