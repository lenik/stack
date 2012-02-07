package com.bee32.sem.inventory.tx.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.people.dto.OrgDto;

public class StockOutsourcingDto
        extends StockJobDto<StockOutsourcing> {

    private static final long serialVersionUID = 1L;

    public static final int ORDER_ITEMS = 1;

    StockOrderDto output;
    StockOrderDto input;
    OrgDto processedBy;

    @Override
    protected void _marshal(StockOutsourcing s) {
        if (selection.contains(ORDERS)) {
            int orderSelection = selection.translate(ITEMS, StockOrderDto.ITEMS);
            output = mref(StockOrderDto.class, orderSelection, s.getOutput());
            input = mref(StockOrderDto.class, orderSelection, s.getInput());
        }
        processedBy = mref(OrgDto.class, s.getProcessedBy());
    }

    @Override
    protected void _unmarshalTo(StockOutsourcing target) {
        if (selection.contains(ORDERS)) {
            merge(target, "output", output);
            merge(target, "input", input);
        }
        merge(target, "processedBy", processedBy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public StockOrderDto getOutput() {
        return output;
    }

    public void setOutput(StockOrderDto output) {
        this.output = output;
    }

    public StockOrderDto getInput() {
        return input;
    }

    public void setInput(StockOrderDto input) {
        this.input = input;
    }

    public OrgDto getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(OrgDto processedBy) {
        this.processedBy = processedBy;
    }

}
