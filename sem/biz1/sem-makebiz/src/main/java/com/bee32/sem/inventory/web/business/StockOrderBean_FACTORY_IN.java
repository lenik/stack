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
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.world.monetary.MutableMCValue;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "TKFO"))
public class StockOrderBean_FACTORY_IN extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    MakeOrderDto makeOrder = new MakeOrderDto().create();
    MakeTaskDto makeTask = new MakeTaskDto().create();

    public StockOrderBean_FACTORY_IN() {
        this.subject = StockOrderSubject.FACTORY_IN;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        return false;
    }

    public void setStepItem(MakeStepItemDto stepItem) {
        StockOrderDto stockOrder = this.getOpenedObject();
        stockOrder.setJob(stepItem);

        // 如果生产数据已经有对应的入库单，则进行提示
        stepItem = reload(stepItem, MakeStepItemDto.ORDERS);
        if (stepItem.getStockOrders().size() > 0) {
            uiLogger.info("此生产数据已经有对应的生产出/入库单，请注意！");
        }
    }

    public void imFMakeOrder() {
        StockOrderDto stockOrder = getOpenedObject();
        try {
            MakeOrderDto order = reload(makeOrder, MakeOrderDto.ITEMS);
            List<MakeOrderItemDto> items = order.getItems();
            for (MakeOrderItemDto item : items) {
                // TODO
                StockOrderItemDto orderItem = new StockOrderItemDto();
                orderItem.setParent(stockOrder);
                orderItem.setMaterial(item.getMaterial());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setBatchArray(new BatchArray(""));
                orderItem.setState(StockItemState.NORMAL);
                orderItem.setPrice(new MutableMCValue());

                stockOrder.addItem(orderItem);
            }
        } catch (Exception e) {
            uiLogger.info("取得订单数据错误：" + e.getMessage());
        }
    }

    public void imFMakeTask() {
        StockOrderDto stockOrder = getOpenedObject();

        try {
            MakeTaskDto task = reload(makeTask, MakeTaskDto.ITEMS);
            List<MakeTaskItemDto> taskItems = task.getItems();
            for (MakeTaskItemDto taskItem : taskItems) {
                StockOrderItemDto item = new StockOrderItemDto();
                item.setParent(stockOrder);
                item.setMaterial(taskItem.getMaterial());
                item.setQuantity(taskItem.getQuantity());
                item.setBatchArray(new BatchArray(""));
                item.setState(StockItemState.NORMAL);
                item.setPrice(new MutableMCValue());

                stockOrder.addItem(item);
            }
        } catch (Exception e) {
            uiLogger.info("取得任务单数据错误" + e.getMessage());
        }
    }

    public void importPart() {
        StockOrderDto stockOrder = this.getOpenedObject();
        MakeStepItemDto stepItem = (MakeStepItemDto) stockOrder.getJob();
        if (DTOs.isNull(stepItem)) {
            uiLogger.info("还没有选择生产数据！");
            return;
        }

        try {
            MakeStepModelDto model = stepItem.getParent().getModel();

            StockOrderItemDto item = new StockOrderItemDto();
            item.setParent(stockOrder);
            item.setMaterial(model.getOutput().getTarget());
            item.setQuantity(stepItem.getActualQuantity());
            item.setBatchArray(new BatchArray(""));
            item.setState(StockItemState.NORMAL);
            item.setPrice(new MutableMCValue());

            stockOrder.addItem(item);
        } catch (Exception e) {
            e.printStackTrace();
            uiLogger.info("取得生产数据错误.详细错误:" + e.getMessage());
        }

    }

    public MakeOrderDto getMakeOrder() {
        return makeOrder;
    }

    public void setMakeOrder(MakeOrderDto makeOrder) {
        this.makeOrder = makeOrder;
    }

    public MakeTaskDto getMakeTask() {
        return makeTask;
    }

    public void setMakeTask(MakeTaskDto makeTask) {
        this.makeTask = makeTask;
    }

}
