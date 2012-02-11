package com.bee32.sem.purchase.dto;

import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;

public class StockPlanOrderDto
        extends StockOrderDto {

    private static final long serialVersionUID = 1L;

    public StockPlanOrderDto() {
        super();
    }

    public StockPlanOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected Class<? extends StockJobDto<?>> getStockJobDtoClass() {
        return MaterialPlanDto.class;
    }

}
