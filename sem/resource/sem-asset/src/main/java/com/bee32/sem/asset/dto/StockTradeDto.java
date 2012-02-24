package com.bee32.sem.asset.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.StockTrade;

public class StockTradeDto
        extends AccountTicketItemDto {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int ITEMS_FOR_UPDATE = ITEMS | 2;

    List<StockTradeItemDto> items;

    public StockTradeDto() {
        super();
    }

    public StockTradeDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountTicketItem source) {
        super._marshal(source);
        StockTrade _source = (StockTrade) source;

        if (selection.contains(ITEMS)) {
            if (selection.contains(ITEMS_FOR_UPDATE))
                items = marshalList(StockTradeItemDto.class, _source.getItems());
            else
                items = mrefList(StockTradeItemDto.class, _source.getItems());
        } else
            items = new ArrayList<StockTradeItemDto>();
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        super._unmarshalTo(target);

        StockTrade _target = (StockTrade) target;

        if (selection.contains(ITEMS)) {
            mergeList(_target, "items", items);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<StockTradeItemDto> getItems() {
        return items;
    }

    public void setItems(List<StockTradeItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(StockTradeItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(StockTradeItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        // item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

}
