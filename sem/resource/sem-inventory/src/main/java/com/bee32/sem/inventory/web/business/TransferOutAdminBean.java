package com.bee32.sem.inventory.web.business;

import com.bee32.plover.criteria.hibernate.Equals;
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

    @Override
    protected void openSelection() {
        super.openSelection();
        StockOrderDto stockOrder = getOpenedObject();
        if (stockOrder == null)
            stockTransfer = null;
        else {
            StockTransfer _transfer = serviceFor(StockTransfer.class).getUnique(
                    new Equals("source.id", stockOrder.getId()));
            if (_transfer == null) // unexpected.
                stockTransfer = new StockTransferDto().create();
            else
                stockTransfer = DTOs.marshal(StockTransferDto.class, -1, _transfer);
            stockTransfer.setSource(stockOrder); // this is an optim.
        }
    }

    @Override
    protected StockOrderDto create() {
        StockOrderDto stockOrder = super.create();
        stockTransfer = new StockTransferDto().create();
        return stockOrder;
    }

    @Override
    protected boolean deleteStockJob(StockOrder stockOrder) {
        try {
            serviceFor(StockTransfer.class).findAndDelete(new Equals("source.id", stockOrder.getId()));
            return true;
        } catch (Exception e) {
            uiLogger.error("无法删除相关的库存调拨作业", e);
            return false;
        }
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
        for (StockOrder _stockOrder : uMap.<StockOrder> entitySet()) {
            StockTransfer _stockTransfer = stockTransfer.unmarshal();
            _stockTransfer.setSource(_stockOrder);

            // 在拨出的同时立即初始化拨入。
            StockOrder _dest = _stockTransfer.getDest();
            if (_dest == null) {
                _dest = new StockOrder();
                _dest.setSubject(StockOrderSubject.XFER_IN);
                asFor(StockOrder.class).save(_dest);
                _stockTransfer.setDest(_dest);
            }

            asFor(StockTransfer.class).saveOrUpdate(_stockTransfer);
            break;
        }
    }

}
