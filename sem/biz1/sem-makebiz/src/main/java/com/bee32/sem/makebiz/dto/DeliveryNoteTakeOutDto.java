package com.bee32.sem.makebiz.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.makebiz.entity.DeliveryNoteTakeOut;

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

}
