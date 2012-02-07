package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.service.IStockMergeStrategy;
import com.bee32.sem.inventory.service.SMS_MBLC;
import com.bee32.sem.world.thing.AbstractOrderDto;

public class StockItemListDto
        extends AbstractOrderDto<StockItemList, StockOrderItem, StockOrderItemDto> {

    private static final long serialVersionUID = 1L;

    IStockMergeStrategy mergeStrategy = SMS_MBLC.INSTANCE;

    public StockItemListDto() {
        super();
    }

    public StockItemListDto(int fmask) {
        super(fmask);
    }

    @Override
    public StockItemListDto populate(Object source) {
        if (source instanceof StockItemListDto) {
            StockItemListDto o = (StockItemListDto) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(StockItemListDto o) {
        super._populate(o);
        mergeStrategy = o.mergeStrategy;
    }

    @Override
    protected void _marshal(StockItemList source) {
        mergeStrategy = source.getMergeStrategy();
    }

    @Override
    protected void _unmarshalTo(StockItemList target) {
        // target.setMergeStrategy(mergeStrategy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
