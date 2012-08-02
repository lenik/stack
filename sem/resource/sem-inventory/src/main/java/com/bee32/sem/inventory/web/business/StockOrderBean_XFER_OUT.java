package com.bee32.sem.inventory.web.business;

import java.util.List;

import javax.free.Nullables;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockJobStepping;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRO"))
public class StockOrderBean_XFER_OUT
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public StockOrderBean_XFER_OUT() {
        subject = StockOrderSubject.XFER_OUT;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockTransfer.class);
        stepping.setJobDtoClass(StockTransferDto.class);
        stepping.setInitiatorProperty("source");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("source");
        stepping.setBindingColumn("s1");
        return true;
    }

    boolean noEmptyOrder = false;

    @Override
    protected boolean postValidate(List<?> dtos) {
        StockTransferDto stockTransfer = stepping.getOpenedObject();
        if (Nullables.equals(selectedWarehouseId, stockTransfer.getDestWarehouse().getId())) {
            uiLogger.error("调拨源${tr.inventory.warehouse}和目标${tr.inventory.warehouse}不能相同");
            return false;
        }

        if (noEmptyOrder)
            for (Object dto : dtos) {
                StockOrderDto stockOrder = (StockOrderDto) dto;
                if (stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
                    uiLogger.error("单据上至少应该有一条明细");
                    return false;
                }
            }
        return true;
    }

    @Override
    protected void initStockJob(StockJobDto<?> stockJob) {
        super.initStockJob(stockJob);

        StockTransferDto _dto = (StockTransferDto)stockJob;
        _dto.setSourceWarehouse(getSelectedWarehouse());
    }



}
