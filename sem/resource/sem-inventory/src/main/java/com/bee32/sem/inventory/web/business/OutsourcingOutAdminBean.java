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
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "OSPO"))
public class OutsourcingOutAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private StockOutsourcingDto stockOutsourcing = new StockOutsourcingDto().create();

    public OutsourcingOutAdminBean() {
        this.subject = StockOrderSubject.OSP_OUT;
    }

    public StockOutsourcingDto getStockOutsourcing() {
        return stockOutsourcing;
    }

    public void setStockOutsourcing(StockOutsourcingDto stockOutsourcing) {
        this.stockOutsourcing = stockOutsourcing;
    }

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
    }

    private void loadStockOrder(int position) {
        stockOrder = new StockOrderDto().create();
        stockOutsourcing = new StockOutsourcingDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()), //
                    Order.asc("id"));

            if (firstOrder != null) {
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);

                StockOutsourcing o = serviceFor(StockOutsourcing.class).getUnique(
                        new Equals("output.id", stockOrder.getId()));
                if (o != null) {
                    stockOutsourcing = DTOs.marshal(StockOutsourcingDto.class, o);
                }
            }
        }
    }

    public void new_() {
        if (selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择对应的仓库!");
            return;
        }

        stockOutsourcing = new StockOutsourcingDto().create();
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

    public void delete() {
        try {
            serviceFor(StockOutsourcing.class).findAndDelete(new Equals("output.id", stockOrder.getId()));
            // serviceFor(StockOrder.class).delete(stockOrder.unmarshal());
            uiLogger.info("删除成功!");
            loadStockOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    @Transactional
    public void save() {
        stockOrder.setWarehouse(selectedWarehouse);

        if (stockOrder.getId() == null) {
            // 新增
            goNumber = count + 1;
        }
        try {
            stockOutsourcing.setOutput(stockOrder);
            StockOutsourcing _stockOutsourcing = stockOutsourcing.unmarshal();
            StockOrder _order = _stockOutsourcing.getInput();

            for (StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            // 保存stockOutsourcing
            serviceFor(StockOutsourcing.class).saveOrUpdate(_stockOutsourcing);

            uiLogger.info("保存成功");
            loadStockOrder(goNumber);
            editable = false;
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
