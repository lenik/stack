package com.bee32.sem.inventory.tx.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.process.base.ProcessEntityDto;

public class StockJobDto<E extends StockJob>
        extends ProcessEntityDto<E> {

    private static final long serialVersionUID = 1L;

    public static final int ORDERS = 0x10000;
    public static final int ORDER_ITEMS = ORDERS | 0x20000;

    List<StockOrderDto> stockOrders;

    public StockJobDto() {
        super();
    }

    public StockJobDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        if (selection.contains(ORDERS)) {
            stockOrders = new ArrayList<StockOrderDto>();
            for (AbstractStockOrder<?> _stockOrder : source.getStockOrders()) {
                Class<StockOrderDto> dtoClass = (Class<StockOrderDto>) EntityUtil.getDtoType(_stockOrder.getClass());
                StockOrderDto stockOrder = marshal(dtoClass, selection.translate(//
                        ORDER_ITEMS, StockOrderDto.ITEMS), //
                        _stockOrder);
                stockOrders.add(stockOrder);
            }
        } else
            stockOrders = Collections.emptyList();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        if (selection.contains(ORDERS))
            mergeList(target, "stockOrders", stockOrders);
    }

    @Override
    protected void _marshal(E source) {
    }

    @Override
    protected void _unmarshalTo(E target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public List<StockOrderDto> getStockOrders() {
        return stockOrders;
    }

    public void setStockOrders(List<StockOrderDto> stockOrders) {
        if (stockOrders == null)
            throw new NullPointerException("stockOrders");
        this.stockOrders = stockOrders;
    }

    public StockOrderDto getStockOrder() {
        if (stockOrders.isEmpty())
            return null;
        else
            return stockOrders.get(0);
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        stockOrders.clear();
        if (stockOrder != null)
            stockOrders.add(stockOrder);
    }

}
