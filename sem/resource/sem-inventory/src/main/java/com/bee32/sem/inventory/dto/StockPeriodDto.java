package com.bee32.sem.inventory.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.entity.StockPeriodType;

public class StockPeriodDto
        extends UIEntityDto<StockPeriod, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int PACK_ORDERS = 1;
    public static final int ORDERS = 2;

    // StockInventoryDto inventory;
    StockPeriodType type = StockPeriodType.INITIAL;

    boolean checkedOut;

    List<StockOrderDto> packOrders;
    List<StockOrderDto> orders;

    @Override
    protected void _marshal(StockPeriod source) {
        // inventory = mref(source.getInventory());

        type = source.getType();
        checkedOut = source.isCheckedOut();

        if (selection.contains(PACK_ORDERS))
            packOrders = marshalList(StockOrderDto.class, source.getPackOrders(), true);

        if (selection.contains(ORDERS))
            orders = marshalList(StockOrderDto.class, source.getOrders(), true);
    }

    @Override
    protected void _unmarshalTo(StockPeriod target) {
        target.setType(type);
        target.setCheckedOut(checkedOut);

        if (selection.contains(PACK_ORDERS))
            mergeList(target, "packOrders", packOrders);

        if (selection.contains(ORDERS))
            mergeList(target, "orders", orders);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
