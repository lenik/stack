package com.bee32.sem.inventory.tx.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.tx.entity.StockJob;

public abstract class StockJobDto<E extends StockJob>
        extends TxEntityDto<E> {

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
        if (selection.contains(ORDERS)) {
            stockOrders = new ArrayList<StockOrderDto>();
            for (AbstractStockOrder<?, ?> _stockOrder : source.getStockOrders()) {
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
        if (selection.contains(ORDERS))
            mergeList(target, "stockOrders", stockOrders);
    }

    public List<StockOrderDto> getStockOrders() {
        return stockOrders;
    }

    public void setStockOrders(List<StockOrderDto> stockOrders) {
        if (stockOrders == null)
            throw new NullPointerException("stockOrders");
        this.stockOrders = stockOrders;
    }

}
