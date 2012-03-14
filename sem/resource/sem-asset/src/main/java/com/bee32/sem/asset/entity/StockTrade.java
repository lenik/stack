package com.bee32.sem.asset.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;

@Entity
@DiscriminatorValue(".ST")
public abstract class StockTrade
        extends AccountTicketItem {

    private static final long serialVersionUID = 1L;

    List<StockTradeItem> items = new ArrayList<StockTradeItem>();

    @Override
    public void populate(Object source) {
        if (source instanceof StockTrade)
            _populate((StockTrade) source);
        else
            super.populate(source);
    }

    protected void _populate(StockTrade o) {
        super._populate(o);
        items = CopyUtils.copyList(o.items);
    }

    @OneToMany(mappedBy = "trade", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<StockTradeItem> getItems() {
        return items;
    }

    public void setItems(List<StockTradeItem> items) {
        this.items = items;
    }

    public synchronized void addItem(StockTradeItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(StockTradeItem item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

}
