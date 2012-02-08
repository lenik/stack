package com.bee32.sem.inventory.web.business;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRI"))
public class TransferInAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private StockOrderDto stockOrderOut = new StockOrderDto().ref();
    private StockTransferDto stockTransfer = new StockTransferDto().create();
    private StockOrderItemDto selectedItemIn;

    private int countOut;

    private boolean transferring; // 是否在拨入状态

    public TransferInAdminBean() {
        subject = StockOrderSubject.XFER_IN;
        transferring = false;
    }

    @Override
    protected void configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockTransfer.class);
        stepping.setJobDtoClass(StockTransferDto.class);
        stepping.setInitiatorProperty("source");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("dest");
        stepping.setBindingColumn("s2");
    }

    public StockOrderDto getStockOrderOut() {
        return stockOrderOut;
    }

    public void setStockOrderOut(StockOrderDto stockOrderOut) {
        this.stockOrderOut = stockOrderOut;
    }

    public StockTransferDto getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferDto stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    public String getCreatorOut() {
        if (stockOrderOut == null)
            return "";
        else
            return stockOrderOut.getOwnerDisplayName();
    }

    public List<StockOrderItemDto> getItemsOut() {
        if (stockOrderOut == null)
            return null;
        return stockOrderOut.getItems();
    }

    public boolean isTransferring() {
        return transferring;
    }

    public void setTransferring(boolean transferring) {
        this.transferring = transferring;
    }

    public StockOrderItemDto getSelectedItemIn() {
        return selectedItemIn;
    }

    public void setSelectedItemIn(StockOrderItemDto selectedItemIn) {
        this.selectedItemIn = selectedItemIn;
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (StockOrderDto stockOrder : uMap.<StockOrderDto> dtos()) {
            // 若加入事务标记后，清空StockTransfer.dest后，不会马上反应到数据库中，
            // 导致StockOrder被引用，不能删除，会出错
            StockTransfer transfer = serviceFor(StockTransfer.class).getUnique(
                    new Equals("dest.id", stockOrder.getId()));
            if (transfer != null) {
                transfer.setDest(null);
                try {
                    serviceFor(StockTransfer.class).saveOrUpdate(transfer);
                } catch (Exception e) {
                    uiLogger.error("Can't detach stock-transfer", e);
                    return false;
                }
            }
        }
        return true;
    }

    public void transferInStart() {
        if (countOut <= 0) {
            uiLogger.warn("没有可以拨入的单据");
            return;
        }

        StockOrderDto stockOrder = getOpenedObject();
        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
// stockTransfer.setSourceWarehouse(stockTransferOut.getSourceWarehouse());

        transferring = true;
    }

    @Transactional
    public void transferInDone() {
        StockOrderDto stockOrder = getOpenedObject();
        // if(stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
        // uiLogger.warn("单据上至少应该有一条明细");
        // return;
        // }

        stockOrder.setWarehouse(getSelectedWarehouse());

// stockTransferOut.setDestWarehouse(getSelectedWarehouse());
// stockTransferOut.setDest(stockOrder);
// StockTransfer _stockTransferOut = stockTransferOut.unmarshal();
        // 保存stockTransferOut
        try {
// serviceFor(StockTransfer.class).saveOrUpdate(_stockTransferOut);

            uiLogger.info("拨入成功");

            transferring = false;
        } catch (Exception e) {
            uiLogger.warn("拨入失败,错误信息:" + e.getMessage());
        }
    }


}
