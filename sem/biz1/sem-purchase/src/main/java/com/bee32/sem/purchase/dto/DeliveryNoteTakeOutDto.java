package com.bee32.sem.purchase.dto;

import java.util.Arrays;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.purchase.entity.DeliveryNoteTakeOut;

public class DeliveryNoteTakeOutDto
        extends StockJobDto<DeliveryNoteTakeOut> {

    private static final long serialVersionUID = 1L;

    DeliveryNoteDto deliveryNote;

    @Override
    protected void _marshal(DeliveryNoteTakeOut source) {
        deliveryNote = mref(DeliveryNoteDto.class, source.getDeliveryNote());
    }

    @Override
    protected void _unmarshalTo(DeliveryNoteTakeOut target) {
        merge(target, "deliveryNote", deliveryNote);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public DeliveryNoteDto getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNoteDto deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public StockOrderDto getStockOrder() {
        if (getStockOrders().isEmpty())
            return null;
        else
            return getStockOrders().get(0);
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        if (stockOrder == null)
            throw new NullPointerException("stockOrder");
        setStockOrders(Arrays.asList(stockOrder));
    }

}
