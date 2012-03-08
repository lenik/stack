package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;

public class PurchaseTakeInDto
        extends StockJobDto<PurchaseTakeIn> {

    private static final long serialVersionUID = 1L;

    PurchaseRequestDto purchaseRequest;

    @Override
    protected void _marshal(PurchaseTakeIn source) {
        purchaseRequest = mref(PurchaseRequestDto.class, source.getPurchaseRequest());
    }

    @Override
    protected void _unmarshalTo(PurchaseTakeIn target) {
        merge(target, "purchaseRequest", purchaseRequest);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PurchaseRequestDto getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequestDto purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
