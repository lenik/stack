package com.bee32.sem.inventory.tx.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.material.dto.StockWarehouseDto;
import com.bee32.sem.people.dto.PersonDto;

public class StockTransferDto
        extends StockJobDto<StockTransfer> {

    private static final long serialVersionUID = 1L;

    StockOrderDto source;
    StockOrderDto dest;
    PersonDto transferredBy;

    StockWarehouseDto sourceWarehouse;
    StockWarehouseDto destWarehouse;

    @Override
    protected void _marshal(StockTransfer s) {
        if (selection.contains(ORDERS)) {
            int orderSelection = selection.translate(ORDER_ITEMS, StockOrderDto.ITEMS);
            source = mref(StockOrderDto.class, orderSelection, s.getSource());
            dest = mref(StockOrderDto.class, orderSelection, s.getDest());
        }

        transferredBy = mref(PersonDto.class, s.getTransferredBy());

        sourceWarehouse = mref(StockWarehouseDto.class, s.getSourceWarehouse());
        destWarehouse = mref(StockWarehouseDto.class, s.getDestWarehouse());
    }

    @Override
    protected void _unmarshalTo(StockTransfer target) {
        if (selection.contains(ORDERS)) {
            merge(target, "source", source);
            merge(target, "dest", dest);
        }

        merge(target, "transferredBy", transferredBy);

        merge(target, "sourceWarehouse", sourceWarehouse);
        merge(target, "destWarehouse", destWarehouse);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public StockOrderDto getSource() {
        return source;
    }

    public void setSource(StockOrderDto source) {
        this.source = source;
    }

    public StockOrderDto getDest() {
        return dest;
    }

    public void setDest(StockOrderDto dest) {
        this.dest = dest;
    }

    public PersonDto getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(PersonDto transferredBy) {
        this.transferredBy = transferredBy;
    }

    public StockWarehouseDto getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouseDto sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    public StockWarehouseDto getDestWarehouse() {
        return destWarehouse;
    }

    public void setDestWarehouse(StockWarehouseDto destWarehouse) {
        this.destWarehouse = destWarehouse;
    }

}
