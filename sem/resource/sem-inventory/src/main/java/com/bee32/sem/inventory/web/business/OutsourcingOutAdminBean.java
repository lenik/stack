package com.bee32.sem.inventory.web.business;

import javax.faces.event.AjaxBehaviorEvent;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;

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
// loadStockOrder(goNumber);
    }

    private void loadStockOrder(int position) {
        StockOrderDto stockOrder = new StockOrderDto().create();
        stockOutsourcing = new StockOutsourcingDto().create();
        if (selectedWarehouseId != -1) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
// StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouseId), //
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

    public void delete() {
        StockOrderDto stockOrder = getOpenedObject();
        try {
            serviceFor(StockOutsourcing.class).findAndDelete(new Equals("output.id", stockOrder.getId()));
            // serviceFor(StockOrder.class).delete(stockOrder.unmarshal());
            uiLogger.info("删除成功!");
// loadStockOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    public void save1() {
        StockOrderDto stockOrder = getOpenedObject();
        if (stockOrder.getId() == null) {
            // 新增
// goNumber = count + 1;
        }
        try {
            stockOutsourcing.setOutput(stockOrder);
            StockOutsourcing _stockOutsourcing = stockOutsourcing.unmarshal();
            StockOrder _order = _stockOutsourcing.getInput();

            // 保存stockOutsourcing
            serviceFor(StockOutsourcing.class).saveOrUpdate(_stockOutsourcing);

            uiLogger.info("保存成功");
// loadStockOrder(goNumber);
// editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

}
