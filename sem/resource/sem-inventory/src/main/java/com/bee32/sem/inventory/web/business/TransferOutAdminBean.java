package com.bee32.sem.inventory.web.business;

import javax.free.Nullables;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRO"))
public class TransferOutAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public TransferOutAdminBean() {
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
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        StockTransferDto stockTransfer = stepping.getOpenedObject();
        if (Nullables.equals(selectedWarehouseId, stockTransfer.getDestWarehouse().getId())) {
            uiLogger.error("调拨源仓库和目标仓库不能相同");
            return false;
        }
        if (noEmptyOrder)
            for (StockOrderDto stockOrder : uMap.<StockOrderDto> dtos()) {
                if (stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
                    uiLogger.error("单据上至少应该有一条明细");
                    return false;
                }
            }
        return true;
    }

}
