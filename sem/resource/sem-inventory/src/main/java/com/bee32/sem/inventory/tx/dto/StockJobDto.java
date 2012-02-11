package com.bee32.sem.inventory.tx.dto;

import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.tx.entity.StockJob;

public abstract class StockJobDto<E extends StockJob>
        extends TxEntityDto<E> {

    private static final long serialVersionUID = 1L;

    public static final int ORDERS = 0x10000;
    public static final int ORDER_ITEMS = ORDERS | 0x20000;

    public StockJobDto() {
        super();
    }

    public StockJobDto(int fmask) {
        super(fmask);
    }

}
