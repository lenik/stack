package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "TKFO"))
public class StockOrderBean_FACTORY_OUT
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public StockOrderBean_FACTORY_OUT() {
        this.subject = StockOrderSubject.FACTORY_OUT;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        return false;
    }

    public void setStepItem(MakeStepItemDto stepItem) {
        StockOrderDto stockOrder = this.getOpenedObject();
        stockOrder.setJob(stepItem);

        //如果生产数据已经有对应的出库单，则进行提示
        stepItem = reload(stepItem, MakeStepItemDto.ORDERS);
        if (stepItem.getStockOrders().size()>0) {
            uiLogger.info("此生产数据已经有对应的生产出库单，请注意！");
        }
    }



}
