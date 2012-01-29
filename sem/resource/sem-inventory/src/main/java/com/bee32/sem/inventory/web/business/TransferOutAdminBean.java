package com.bee32.sem.inventory.web.business;

import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockCriteria;

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

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
    }

    private void loadStockOrder(int position) {
        stockOrder = new StockOrderDto().create();
        stockTransfer = new StockTransferDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()), //
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

    public void new_() {
        if (selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择对应的仓库!");
            return;
        }

        stockTransfer = new StockTransferDto().create();
        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
        // stockOrder.setCreatedDate(new Date());
        editable = true;
    }

    public void modify() {
        if (stockOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    @Transactional
    public void delete() {
        try {
            serviceFor(StockTransfer.class).findAndDelete(new Equals("source.id", stockOrder.getId()));
            // serviceFor(StockOrder.class).deleteById(stockOrder.getId());
            uiLogger.info("删除成功!");
            loadStockOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    @Transactional
    public void save() {
        if (selectedWarehouse.getId().equals(stockTransfer.getDestWarehouse().getId())) {
            uiLogger.warn("调拨源仓库和目标仓库不能相同");
            return;
        }

// if(stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
// uiLogger.warn("单据上至少应该有一条明细");
// return;
// }

        stockOrder.setWarehouse(selectedWarehouse);

        if (stockOrder.getId() == null) {
            // 新增
            goNumber = count + 1;
        }

        try {
            stockTransfer.setSourceWarehouse(selectedWarehouse);
            stockTransfer.setSource(stockOrder);
            StockTransfer _stockTransfer = stockTransfer.unmarshal();

            StockOrder _order = _stockTransfer.getSource();

            // 删除需要删除的item
            for (StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            // 保存stockTransfer
            serviceFor(StockTransfer.class).saveOrUpdate(_stockTransfer);

            uiLogger.info("保存成功");
            loadStockOrder(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void choosePerson() {
        stockTransfer.setTransferredBy(selectedPerson);
    }

    @Override
    public StockOrderItemDto getOrderItem_() {
        return orderItem;
    }

    @Override
    public StockWarehouseDto getSelectedWarehouse_() {
        return selectedWarehouse;
    }
}
