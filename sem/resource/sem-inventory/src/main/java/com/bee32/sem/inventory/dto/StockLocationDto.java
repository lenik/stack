package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.world.thing.UnitDto;

public class StockLocationDto
        extends TreeEntityDto<StockLocation, Integer, StockLocationDto> {

    private static final long serialVersionUID = 1L;

    private StockWarehouseDto warehouse;
    private String address;
    private double x;
    private double y;
    private double z;

    BigDecimal capacity;
    UnitDto capacityUnit;
    String capacityUnitHint;
    int rank;

    public StockLocationDto() {
        super();
    }

    public StockLocationDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(StockLocation source) {
        this.warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
        this.address = source.getAddress();
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getZ();
        this.capacity = source.getCapacity();
        this.capacityUnit = mref(UnitDto.class, source.getCapacityUnit());
        this.capacityUnitHint = source.getCapacityUnitHint();
        this.rank = source.getRank();
    }

    @Override
    protected void _unmarshalTo(StockLocation target) {
        merge(target, "warehouse", warehouse);
        target.setAddress(address);
        target.setX(x);
        target.setY(y);
        target.setZ(z);
        target.setCapacity(capacity);
        merge(target, "capacityUnit", capacityUnit);
        target.setCapacityUnitHint(capacityUnitHint);
        target.setRank(rank);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        this.warehouse = warehouse;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }



    public UnitDto getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(UnitDto capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

    public String getCapacityUnitHint() {
        return capacityUnitHint;
    }

    public void setCapacityUnitHint(String capacityUnitHint) {
        this.capacityUnitHint = capacityUnitHint;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNodeText() {
        StringBuilder sb = new StringBuilder();
        if (getLabel() != null && !getLabel().isEmpty())
            sb.append(getLabel());
        if (getLabel() != null && !getLabel().isEmpty() && address != null && !address.isEmpty())
            sb.append(":");
        if (address != null && !address.isEmpty())
            sb.append(address);
        return sb.toString();
    }
}
