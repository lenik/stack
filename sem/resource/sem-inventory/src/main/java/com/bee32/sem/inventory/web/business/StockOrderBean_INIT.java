package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockJobStepping;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "INIT"))
public class StockOrderBean_INIT
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public StockOrderBean_INIT() {
        subject = StockOrderSubject.INIT;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        return false;
    }

}
