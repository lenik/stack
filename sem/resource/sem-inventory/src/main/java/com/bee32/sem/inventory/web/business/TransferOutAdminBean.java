package com.bee32.sem.inventory.web.business;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRO"))
public class TransferOutAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private StockTransferDto stockTransfer = new StockTransferDto().create();

    public TransferOutAdminBean() {
        subject = StockOrderSubject.XFER_OUT;
    }

    public StockTransferDto getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferDto stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    private void loadStockOrder(int position) {
        StockOrderDto stockOrder = new StockOrderDto().create();
        stockTransfer = new StockTransferDto().create();
        if (selectedWarehouseId != -1) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
// StockCriteria.subjectOf(getSubject()), //
// new Equals("warehouse.id", selectedWarehouse.getId()), //
                    Order.asc("id"));

            if (firstOrder != null) {
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);

                StockTransfer t = serviceFor(StockTransfer.class)
                        .getUnique(new Equals("source.id", stockOrder.getId()));
                if (t != null) {
                    stockTransfer = DTOs.marshal(StockTransferDto.class, -1, t);
                }
            }
        }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (StockOrderDto stockOrder : uMap.<StockOrderDto> dtos()) {
            try {
                serviceFor(StockTransfer.class).findAndDelete(new Equals("source.id", stockOrder.getId()));
            } catch (Exception e) {
                uiLogger.error("Can't delete stock-transfer", e);
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        if (selectedWarehouseId == stockTransfer.getDestWarehouse().getId()) {
            uiLogger.error("调拨源仓库和目标仓库不能相同");
            return false;
        }
        for (StockOrderDto stockOrder : uMap.<StockOrderDto> dtos()) {
            if (stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
                uiLogger.error("单据上至少应该有一条明细");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap) {
        StockOrderDto stockOrder = getActiveObject();
        stockTransfer.setSourceWarehouse(getSelectedWarehouse());
        stockTransfer.setSource(stockOrder);
        StockTransfer _stockTransfer = stockTransfer.unmarshal();
        StockOrder _order = _stockTransfer.getSource();
        // save(_stockTransfer);
    }

    public void choosePerson() {
        // stockTransfer.setTransferredBy(selectedPerson);
    }

}
