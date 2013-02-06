package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "TKFO"))
public class StockOrderBean_FACTORY_OUT
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    MakeStepItemDto setpItem;

    public StockOrderBean_FACTORY_OUT() {
        this.subject = StockOrderSubject.FACTORY_OUT;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        return false;
    }

    public void setMakeStepItem(MakeStepItemDto stepItem) {
        this.getJob().setOpenedObject(stepItem);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (StockOrder _order : uMap.<StockOrder> entitySet()) {
            //_order.setJob(this.getJob().getOpenedObject());
        }
        return true;
    }



}
