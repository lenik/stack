package com.bee32.sem.inventory.web.business;

import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(value = StockOrder.class, parameters = //
@TypeParameter(name = "_subject", value = { "TK_I", "TK_O", "TKFI", "TKFO" }))
public class TakeAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public TakeAdminBean() {
        String s = getRequest().getParameter("subject");
        subject = s == null ? null : StockOrderSubject.valueOf(s);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));

        Integer warehouseId = null;
        { // get nothing if warehouse isn't selected.
            if (selectedWarehouse != null)
                warehouseId = selectedWarehouse.getId();
            if (warehouseId == null)
                warehouseId = -1;
            elements.add(new Equals("warehouse.id", warehouseId));
        }
    }

    public void new_() {
        if (selectedWarehouse.getId() == null) {
            uiLogger.error("请选择对应的仓库!");
            return;
        }

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

        if (stockOrder.getEntityFlags().isLocked()) {
            uiLogger.error("单据已经锁定，不能修改!");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();
        editable = true;
    }

    // @Transactional
    public void save1() {
        stockOrder.setWarehouse(selectedWarehouse);

        try {
            StockOrder _order = stockOrder.unmarshal();
            for (StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            serviceFor(StockOrder.class).save(_order);
            uiLogger.info("保存成功");
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
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
