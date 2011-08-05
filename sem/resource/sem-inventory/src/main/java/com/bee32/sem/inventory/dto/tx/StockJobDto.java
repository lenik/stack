package com.bee32.sem.inventory.dto.tx;

import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.inventory.tx.entity.StockJob;

public abstract class StockJobDto<E extends StockJob> extends TxEntityDto<E> {

    private static final long serialVersionUID = 1L;

    public StockJobDto() {
        super();
    }

    public StockJobDto(int selection) {
        super(selection);
    }


}
