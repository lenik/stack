package com.bee32.sem.inventory.dto;

import javax.free.Nullables;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.AbstractStockItemList;
import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderVerifySupportDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class StockOrderDto
        extends StockItemListDto
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    StockPeriodDto base;
    StockPeriodDto spec;
    StockOrderSubject subject;
    StockJobDto<?> job;

    PartyDto org;
    OrgUnitDto orgUnit;
    StockWarehouseDto warehouse;

    StockOrderVerifySupportDto verifyContext;

    public StockOrderDto() {
        super();
    }

    public StockOrderDto(int fmask) {
        super(fmask);
    }

    @SuppressWarnings("unchecked")
    protected Class<? extends StockJobDto<?>> getStockJobDtoClass() {
        return (Class<? extends StockJobDto<?>>) (Class<?>) StockJobDto.class;
    }

    @Override
    protected void _copy() {
        for (StockOrderItemDto item : getItems())
            item.setParent(this);
    }

    @Override
    protected void _marshal(AbstractStockItemList<?> _source) {
        super._marshal(_source);
        AbstractStockOrder<?> source = (AbstractStockOrder<?>) _source;
        base = mref(StockPeriodDto.class, source.getBase());
        spec = mref(StockPeriodDto.class, source.getSpec());
        subject = source.getSubject();

        @SuppressWarnings("unchecked")
        Class<StockJobDto<StockJob>> stockJobDtoClass = (Class<StockJobDto<StockJob>>) (Object) getStockJobDtoClass();
        StockJob stockJob = source.getJob();
        job = mref(stockJobDtoClass, stockJob);

        org = mref(PartyDto.class, source.getOrg());
        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
        verifyContext = marshal(StockOrderVerifySupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(AbstractStockItemList<?> _target) {
        super._unmarshalTo(_target);
        AbstractStockOrder<?> target = (AbstractStockOrder<?>) _target;
        merge(target, "base", base);
        merge(target, "spec", spec);
        target.setSubject(subject);
        merge(target, "job", job);
        merge(target, "org", org);
        merge(target, "orgUnit", orgUnit);
        merge(target, "warehouse", warehouse);
        merge(target, "verifyContext", verifyContext);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

    @Override
    protected void attach(StockOrderItemDto item) {
        item.setParent(this);
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

    public StockJobDto<?> getJob() {
        return job;
    }

    public void setJob(StockJobDto<?> job) {
        this.job = job;
    }

    public PartyDto getOrg() {
        return org;
    }

    public void setOrg(PartyDto org) {
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
        return verifyContext;
    }

    public void setVerifyContext(StockOrderVerifySupportDto stockOrderVerifySupport) {
        this.verifyContext = stockOrderVerifySupport;
    }

}
