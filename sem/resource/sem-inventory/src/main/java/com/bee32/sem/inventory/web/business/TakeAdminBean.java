package com.bee32.sem.inventory.web.business;

import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

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
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = { "TK_I", "TK_O", "TKFI", "TKFO" }))
public class TakeAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private int goNumber;
    private int count;

    public TakeAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;

        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            String s = req.getParameter("subject").toString();
            subject = StockOrderSubject.valueOf(s);

            String strId = req.getParameter("id");
            if (strId != null) {
                // 说明有传入StockOrder的id,需要直接跳到相应的StockOrder
                StockOrder directOrder = serviceFor(StockOrder.class).get(Long.parseLong(strId));
                StockWarehouse w = directOrder.getWarehouse();
                selectedWarehouse = DTOs.marshal(StockWarehouseDto.class, w);
                limitDateFrom = w.getCreatedDate();
                limitDateTo = w.getCreatedDate();
                if (directOrder.getSubject() != subject) {
                    throw new SubjectNotSameException();
                }
                stockOrder = DTOs.marshal(StockOrderDto.class, directOrder);
            }

        } catch (Exception e) {
            uiLogger.warn("发生错误.", e);
        }
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getCount() {
        count = serviceFor(StockOrder.class).count(//
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                StockCriteria.subjectOf(getSubject()), //
                new Equals("warehouse.id", selectedWarehouse.getId()));
        return count;
    }

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
        loadStockLocationTree();
    }

    private void loadStockOrder(int position) {
        // 刷新总记录数
        getCount();

        goNumber = position;

        if (position < 1) {
            goNumber = 1;
            position = 1;
        }
        if (goNumber > count) {
            goNumber = count;
            position = count;
        }

        stockOrder = new StockOrderDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()), //
                    Order.asc("id"));

            if (firstOrder != null)
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);
        }
    }

    public void limit() {
        loadStockOrder(goNumber);
    }

    public void new_() {
        if (selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择对应的仓库!");
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

    public void delete() {
        try {
            serviceFor(StockOrder.class).delete(stockOrder.unmarshal());
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
            StockOrder _order = stockOrder.unmarshal();
            for (StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            serviceFor(StockOrder.class).save(_order);
            uiLogger.info("保存成功");
            loadStockOrder(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void cancel() {

        loadStockOrder(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadStockOrder(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadStockOrder(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadStockOrder(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadStockOrder(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadStockOrder(goNumber);
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
