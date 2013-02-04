package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.util.StockJobStepping;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "TKFO"))
public class StockOrderBean_FACTORY_OUT
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public StockOrderBean_FACTORY_OUT() {
        this.subject = StockOrderSubject.FACTORY_OUT;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockOutsourcing.class);
        stepping.setJobDtoClass(StockOutsourcingDto.class);
        stepping.setInitiatorProperty("output");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("output");
        stepping.setBindingColumn("s1");
        return true;
    }

}
