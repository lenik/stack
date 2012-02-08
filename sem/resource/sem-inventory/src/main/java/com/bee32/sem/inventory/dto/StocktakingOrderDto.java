package com.bee32.sem.inventory.dto;


public class StocktakingOrderDto
        extends StockOrderDto {

    private static final long serialVersionUID = 1L;

    public StocktakingOrderDto() {
        super();
    }

    public StocktakingOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected Class<? extends StockOrderItemDto> getItemDtoClass() {
        return StocktakingOrderItemDto.class;
    }

}
