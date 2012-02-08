package com.bee32.sem.inventory.dto;

import javax.free.Nullables;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.AbstractStockItemList;
import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderVerifySupportDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class StockOrderDto
        extends StockItemListDto
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

    public StockOrderDto() {
        super();
    }

    public StockOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    public StockOrderDto populate(Object source) {
        if (source instanceof StockOrderDto) {
            StockOrderDto o = (StockOrderDto) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(StockOrderDto o) {
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
    protected void _marshal(AbstractStockItemList<?> _source) {
        super._marshal(_source);
        AbstractStockOrder<?> source = (AbstractStockOrder<?>) _source;
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
    protected void _unmarshalTo(AbstractStockItemList<?> _target) {
        super._unmarshalTo(_target);
        AbstractStockOrder<?> target = (AbstractStockOrder<?>) _target;
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
        super._parse(map);
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
        if (!Nullables.equals(this.org, org)) {
            this.org = org;
            this.orgUnit = new OrgUnitDto().ref();
        }
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
