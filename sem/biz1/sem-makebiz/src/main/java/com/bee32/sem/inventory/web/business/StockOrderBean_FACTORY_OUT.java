package com.bee32.sem.inventory.web.business;

import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.make.dto.MakeStepInputDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.world.monetary.MutableMCValue;

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
            uiLogger.info("此生产数据已经有对应的生产出/入库单，请注意！");
        }
    }

    public void importMakeStepInput() {
        StockOrderDto stockOrder = this.getOpenedObject();
        MakeStepItemDto stepItem = (MakeStepItemDto) stockOrder.getJob();
        if(DTOs.isNull(stepItem)) {
            uiLogger.info("还没有选择生产数据！");
            return;
        }

        try {
            MakeStepModelDto model = stepItem.getParent().getModel();
            model = reload(model, MakeStepModelDto.INPUTS);
            List<MakeStepInputDto> inputs = model.getInputs();

            for(MakeStepInputDto input : inputs) {
                StockOrderItemDto item = new StockOrderItemDto();
                item.setParent(stockOrder);
                item.setMaterial(input.getMaterial());
                item.setQuantity(input.getQuantity());
                item.setBatchArray(new BatchArray(""));
                item.setState(StockItemState.NORMAL);
                item.setPrice(new MutableMCValue());

                stockOrder.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            uiLogger.info("取得耗材数据错误.详细错误:" + e.getMessage());
        }

    }

}
