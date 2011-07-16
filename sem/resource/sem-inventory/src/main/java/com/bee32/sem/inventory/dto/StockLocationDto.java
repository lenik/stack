package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.tree.TreeEntityDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.world.thing.Unit;

public class StockLocationDto
        extends TreeEntityDto<StockLocation, Integer, StockLocationDto> {

    private static final long serialVersionUID = 1L;

    StockWarehouse warehouse;
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
    }

    @Override
    protected void _unmarshalTo(StockLocation target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
