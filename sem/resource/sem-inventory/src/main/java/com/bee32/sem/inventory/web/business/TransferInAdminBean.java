package com.bee32.sem.inventory.web.business;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRI"))
public class TransferInAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private StockOrderSubject subjectOut = null;

    private StockOrderDto stockOrderOut = new StockOrderDto().ref();

    private StockTransferDto stockTransfer = new StockTransferDto().create();
    private StockTransferDto stockTransferOut = new StockTransferDto().create();

    private StockOrderItemDto selectedItemIn;

    private int goNumberOut;
    private int countOut;

    private boolean transferring; // 是否在拨入状态

    public TransferInAdminBean() {
        goNumberOut = 1;

        subject = StockOrderSubject.XFER_IN;
        subjectOut = StockOrderSubject.XFER_OUT;

        transferring = false;
    }

    public StockOrderSubject getSubjectOut() {
        return subjectOut;
    }

    public void setSubjectOut(StockOrderSubject subjectOut) {
        this.subjectOut = subjectOut;
    }

    public StockOrderDto getStockOrderOut() {
        return stockOrderOut;
    }

    public void setStockOrderOut(StockOrderDto stockOrderOut) {
        this.stockOrderOut = stockOrderOut;
    }

    public int getGoNumberOut() {
        return goNumberOut;
    }

    public void setGoNumberOut(int goNumberOut) {
        this.goNumberOut = goNumberOut;
    }

    public int getCountOut() {
        countOut = serviceFor(StockTransfer.class).count(//
                new Equals("destWarehouse.id", selectedWarehouseId), new IsNull("dest.id"));
        return countOut;
    }

    public StockTransferDto getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferDto stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    public StockTransferDto getStockTransferOut() {
        return stockTransferOut;
    }

    public void setStockTransferOut(StockTransferDto stockTransferOut) {
        this.stockTransferOut = stockTransferOut;
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

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrderOut(goNumberOut);
    }

    private void loadStockOrder(int position) {
// stockOrder = new StockOrderDto().create();
// stockTransfer = new StockTransferDto().create();
// if (selectedWarehouse != null) {
// StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
// // new Offset(position - 1), //
// // CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
// // StockCriteria.subjectOf(getSubject()), //
// new Equals("warehouse.id", selectedWarehouse.getId()), Order.asc("id"));
//
// if (firstOrder != null) {
// stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);
//
// StockTransfer t = serviceFor(StockTransfer.class).getUnique(new Equals("dest.id",
// stockOrder.getId()));
// if (t != null) {
// stockTransfer = DTOs.marshal(StockTransferDto.class, t);
// }
// }
// }
    }

    private void loadStockOrderOut(int position) {
        // 刷新总记录数
        getCountOut();

        goNumberOut = position;

        if (position < 1) {
            goNumberOut = 1;
            position = 1;
        }
        if (goNumberOut > countOut) {
            goNumberOut = countOut;
            position = countOut;
        }

        stockOrderOut = new StockOrderDto().create();
        stockTransferOut = new StockTransferDto().create();
        if (selectedWarehouseId != -1) {

            StockTransfer firstTransfer = serviceFor(StockTransfer.class).getFirst(new Offset(position - 1),
                    new Equals("destWarehouse.id", selectedWarehouseId), new IsNull("dest.id"));

            if (firstTransfer != null) {
                stockTransferOut = DTOs.marshal(StockTransferDto.class, firstTransfer);

                StockOrder o = serviceFor(StockOrder.class).getUnique(
                        new Equals("id", stockTransferOut.getSource().getId()));
                if (o != null) {
                    stockOrderOut = DTOs.marshal(StockOrderDto.class, o);
                }
            }
        }
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

    // *Out: e.g., nextOut
    public void nextOut() {
        goNumberOut++;

        if (goNumberOut > countOut)
            goNumberOut = countOut;
        loadStockOrderOut(goNumberOut);
    }

    public void transferInStart() {
        if (countOut <= 0) {
            uiLogger.warn("没有可以拨入的单据");
            return;
        }

        StockOrderDto stockOrder = getActiveObject();
        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
        stockTransfer.setSourceWarehouse(stockTransferOut.getSourceWarehouse());

// editable = true;
        transferring = true;
    }

    @Transactional
    public void transferInDone() {
        StockOrderDto stockOrder = getActiveObject();
        // if(stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
        // uiLogger.warn("单据上至少应该有一条明细");
        // return;
        // }

        stockOrder.setWarehouse(getSelectedWarehouse());

        stockTransferOut.setDestWarehouse(getSelectedWarehouse());
        stockTransferOut.setDest(stockOrder);
        StockTransfer _stockTransferOut = stockTransferOut.unmarshal();
        // 保存stockTransferOut
        try {
            serviceFor(StockTransfer.class).saveOrUpdate(_stockTransferOut);

            uiLogger.info("拨入成功");

            // loadStockOrder(count + 1);
            // loadStockOrderOut(goNumberOut);

// editable = false;
            transferring = false;
        } catch (Exception e) {
            uiLogger.warn("拨入失败,错误信息:" + e.getMessage());
        }
    }

    public void cancelTransferIn() {
        // loadStockOrder(goNumber);
        loadStockOrderOut(goNumberOut);

        // editable = false;
        transferring = false;
    }

    public void choosePerson() {
        // stockTransfer.setTransferredBy(selectedPerson);
    }

    public void newItemIn() {
// orderItemIn = new StockOrderItemDto().create();
// orderItemIn.setMaterial(orderItemOut.getMaterial());
// orderItemIn.setBatch(orderItemOut.getBatch());
// orderItemIn.setExpirationDate(orderItemOut.getExpirationDate());
// orderItemIn.setPrice(orderItemOut.getPrice());
    }

}
