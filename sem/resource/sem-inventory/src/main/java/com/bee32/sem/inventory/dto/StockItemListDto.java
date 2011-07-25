package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

public abstract class StockItemListDto<E extends StockItemList>
        extends TxEntityDto<E> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 0x10000;

    List<StockOrderItemDto> items;
    MCVector total;
    BigDecimal nativeTotal;

    @Override
    protected void __marshal(StockItemList source) {
        if (selection.contains(ITEMS))
            items = marshalList(StockOrderItemDto.class, source.getItems()); // cascade..
    }

    @Override
    protected void __unmarshalTo(StockItemList target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
    }

    public List<StockOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<StockOrderItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public MCVector getTotal() {
        if (total == null) {
            for (StockOrderItemDto item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    public void setTotal(MCVector total) {
        if (total == null)
            throw new NullPointerException("total");
        this.total = total;
    }

    public BigDecimal getNativeTotal() {
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

}
