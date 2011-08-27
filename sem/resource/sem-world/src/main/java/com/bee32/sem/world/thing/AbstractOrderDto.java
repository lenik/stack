package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

public abstract class AbstractOrderDto< //
/*        */E extends AbstractOrder<Item>, //
/*        */Item extends AbstractOrderItem, //
/*        */ItemDto extends AbstractOrderItemDto<Item>>
        extends TxEntityDto<E>
        implements ICurrencyAware, DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 0x10000;

    final Class<ItemDto> itemDtoClass = ClassUtil.infer1(getClass(), AbstractOrderDto.class, 2);

    List<ItemDto> items;
    MCVector total;

    BigDecimal nativeTotal;

    public AbstractOrderDto() {
        super();
    }

    public AbstractOrderDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        if (selection.contains(ITEMS))
            items = marshalList(itemDtoClass, source.getItems()); // cascade..
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

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public void addItem(ItemDto item) {
        if (item == null)
            throw new NullPointerException("item");
        items.add(item);
        invalidateTotal();
    }

    public void editItem(ItemDto item) {
        if (item == null)
            throw new NullPointerException("item");
        int index = items.indexOf(item);
        items.set(index, item);
        invalidateTotal();
    }

    public void removeItem(ItemDto item) {
        if (item == null)
            throw new NullPointerException("item");
        items.remove(item);
        invalidateTotal();
    }

    public MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (ItemDto item : items) {
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
                    for (ItemDto item : items) {

                        BigDecimal itemNativeTotal = item.getNativePrice();
                        double d = itemNativeTotal.doubleValue();
                        double q = item.getQuantity().doubleValue();
                        double itemTotal = d * q;
                        BigDecimal bit = new BigDecimal(itemTotal);
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

    protected void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

}
