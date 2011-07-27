package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;

public class MaterialWarehouseOptionDto
        extends EntityDto<MaterialWarehouseOption, Long> {

    private static final long serialVersionUID = 1L;
    public static final int MATERIAL = 1;

    MaterialDto material;
    StockWarehouseDto warehouse;
    BigDecimal safetyStock = new BigDecimal(1);
    int stkPeriod = 365;

    @Override
    protected void _marshal(MaterialWarehouseOption source) {
        if (selection.contains(MATERIAL))
            this.material = mref(MaterialDto.class, source.getMaterial());
        this.warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
        this.safetyStock = source.getSafetyStock();
        this.stkPeriod = source.getStkPeriod();
    }

    @Override
    protected void _unmarshalTo(MaterialWarehouseOption target) {
        merge(target, "material", material);
        merge(target, "warehouse", warehouse);
        target.setSafetyStock(safetyStock);
        target.setStkPeriod(stkPeriod);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) {
        this.safetyStock = safetyStock;
    }

    public int getStkPeriod() {
        return stkPeriod;
    }

    public void setStkPeriod(int stkPeriod) {
        this.stkPeriod = stkPeriod;
    }

    @Override
    protected Boolean naturalEquals(EntityDto<MaterialWarehouseOption, Long> other) {
        MaterialWarehouseOptionDto object = (MaterialWarehouseOptionDto) other;
        if (material == null || warehouse == null)
            return false;
        if (!material.equals(object.getMaterial()))
            return false;
        if (!warehouse.equals(object.getWarehouse()))
            return false;
        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        if (material == null || warehouse == null)
            return System.identityHashCode(this);
        hash += material.hashCode();
        hash += warehouse.hashCode();
        return hash;
    }

}
