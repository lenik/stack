package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.world.thing.Unit;

public class StockLocationDto
        extends TreeEntityDto<StockLocation, Integer, StockLocationDto> {

    private static final long serialVersionUID = 1L;

    StockWarehouseDto warehouse;
    String address;
    double x;
    double y;
    double z;

    BigDecimal capacity;
    Unit capacityUnit = Unit.CUBIC_METER;

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
    }

    @Override
    protected void _unmarshalTo(StockLocation target) {
        merge(target, "warehouse", warehouse);
        target.setAddress(address);
        target.setX(x);
        target.setY(y);
        target.setZ(z);
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

    public Unit getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(Unit capacityUnit) {
        this.capacityUnit = capacityUnit;
    }

}
