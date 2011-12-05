package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.purchase.entity.OrderHolder;

public class OrderHolderDto extends TxEntityDto<OrderHolder> {

    private static final long serialVersionUID = 1L;

    PurchaseRequestDto purchaseRequest;
    StockOrderDto stockOrder;

    @Override
    protected void _marshal(OrderHolder source) {
        purchaseRequest = mref(PurchaseRequestDto.class, source.getPurchaseRequest());
        stockOrder = mref(StockOrderDto.class, source.getStockOrder());
    }

    @Override
    protected void _unmarshalTo(OrderHolder target) {
        merge(target, "purchaseRequest", purchaseRequest);
        merge(target, "stockOrder", stockOrder);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public PurchaseRequestDto getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequestDto purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
    }
}
