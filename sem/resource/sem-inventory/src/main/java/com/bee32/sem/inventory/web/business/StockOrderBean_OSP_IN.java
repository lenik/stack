package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.util.StockJobStepping;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "OSPI"))
public class StockOrderBean_OSP_IN
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public StockOrderBean_OSP_IN() {
        this.subject = StockOrderSubject.OSP_IN;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockOutsourcing.class);
        stepping.setJobDtoClass(StockOutsourcingDto.class);
        stepping.setInitiatorProperty("output");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("input");
        stepping.setBindingColumn("s2");
        return true;
    }

}
