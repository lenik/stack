package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.ICurrencyAware;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

public abstract class AbstractOrderDto< //
/*        */E extends AbstractOrder<_et>, //
/*        */_et extends AbstractOrderItem, //
/*        */_dt extends AbstractOrderItemDto<_et>>
        extends TxEntityDto<E>
        implements ICurrencyAware, DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 0x10000;

    final Class<_dt> itemDtoClass = ClassUtil.infer1(getClass(), AbstractOrderDto.class, 2);

    List<_dt> items;
    MCVector total;

    BigDecimal nativeTotal;

    public AbstractOrderDto() {
        super();
    }

    public AbstractOrderDto(int selection) {
        super(selection);
    }

    @Override
    public AbstractOrderDto<E, _et, _dt> populate(Object source) {
        if (source instanceof AbstractOrderDto<?, ?, ?>) {
            @SuppressWarnings("unchecked")
            AbstractOrderDto<?, _et, _dt> o = (AbstractOrderDto<?, _et, _dt>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(AbstractOrderDto<?, _et, _dt> o) {
        super._populate(o);
        items = new ArrayList<_dt>(); // Never copy (o.items);
        total = o.total;
        nativeTotal = o.nativeTotal;
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        if (selection.contains(ITEMS))
            items = marshalList(itemDtoClass, source.getItems()); // cascade..
        else
            items = new ArrayList<_dt>();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);
    }

    public List<_dt> getItems() {
        return items;
    }

    public void setItems(List<_dt> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public void addItem(_dt item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
        invalidateTotal();
    }

    public void removeItem(_dt item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);

        invalidateTotal();
    }

    public void updateItem(_dt item) {
        if (item == null)
            throw new NullPointerException("item");
        int index = items.indexOf(item);
        if (item.getIndex() == -1)
            item.setIndex(index);
        items.set(index, item);
        invalidateTotal();
    }

    public void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    public MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (_dt item : items) {
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

    public BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            synchronized (this) {
                if (nativeTotal == null) {
                    BigDecimal sum = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);
                    for (_dt item : items) {

                        BigDecimal itemNativeTotal = item.getNativePrice();
                        double d = itemNativeTotal.doubleValue();
                        double q = item.getQuantity().doubleValue();
                        double itemTotal = d * q;
                        BigDecimal bit = new BigDecimal(itemTotal, MONEY_TOTAL_CONTEXT);
                        sum = sum.add(bit);
                        // sum = sum.add(itemNativeTotal);
                    }
                    nativeTotal = sum;
                }
            }
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    public void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

}
