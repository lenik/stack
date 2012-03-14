package com.bee32.sem.inventory.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.entity.AbstractStockItemList;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.service.IStockMergeStrategy;
import com.bee32.sem.inventory.service.SMS_MBLC;
import com.bee32.sem.world.thing.AbstractItemListDto;

public class StockItemListDto
        extends AbstractItemListDto<AbstractStockItemList<?>, StockOrderItem, StockOrderItemDto> {

    private static final long serialVersionUID = 1L;

    IStockMergeStrategy mergeStrategy = SMS_MBLC.INSTANCE;

    public StockItemListDto() {
        super();
    }

    public StockItemListDto(int fmask) {
        super(fmask);
    }

    @Override
    protected Class<? extends StockOrderItemDto> getItemDtoClass() {
        return StockOrderItemDto.class;
    }

    @Override
    protected void _marshal(AbstractStockItemList<?> source) {
        mergeStrategy = source.getMergeStrategy();
    }

    @Override
    protected void _unmarshalTo(AbstractStockItemList<?> target) {
        // target.setMergeStrategy(mergeStrategy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    protected void attach(StockOrderItemDto item) {
        // item.setParent(this);
    }

}
