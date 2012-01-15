package com.bee32.sem.inventory.web.business;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "INIT"))
public class InitAdminBean extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public InitAdminBean() {
        subject = StockOrderSubject.INIT;
    }


    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder();
        loadStockLocationTree();
    }

    private void loadStockOrder() {
        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
        if (selectedWarehouse != null) {
            List<StockOrder> oneList = serviceFor(StockOrder.class).list(
                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()), //
                    Order.desc("id"));

            if (oneList.size() > 0) {
                StockOrder s = oneList.get(0);
                stockOrder = DTOs.marshal(StockOrderDto.class, s);
            }
        }
    }


    public void modify() {
        if(selectedWarehouse == null || selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择对应的仓库");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }


    @Transactional
    public void save() {
        stockOrder.setWarehouse(selectedWarehouse);

        try {
            StockOrder _order = stockOrder.unmarshal();
            for (StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }
            serviceFor(StockOrder.class).save(_order);
            uiLogger.info("保存成功");
            loadStockOrder();
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void cancel() {
        loadStockOrder();
        editable = false;
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
