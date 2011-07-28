package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.GreaterOrEquals;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.world.monetary.CurrencyConfig;

@Component
@Scope("view")
public class StockOrderAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;


    private StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;
    private int goNumber;
    private int count;

    private StockOrderDto stockOrder = new StockOrderDto().ref();

    private StockOrderItemDto orderItem;
    private StockOrderItemDto selectedOrderItem;


    public StockOrderAdminBean() {
        Calendar c = Calendar.getInstance();
        //取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;
    }

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }



    public List<SelectItem> getStockWarehouses() {
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.marshalList(StockWarehouseDto.class, stockWarehouses, true);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (StockWarehouseDto stockWarehouseDto : stockWarehouseDtos) {
            String label = stockWarehouseDto.getName();
            SelectItem item = new SelectItem(stockWarehouseDto.getId(), label);
            items.add(item);
        }

        return items;
    }

    public StockOrderSubject getSubject() {
        StockOrderSubject subject = null;
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String s = req.getParameter("subject").toString();
            subject = StockOrderSubject.valueOf(s);

        } catch (Exception e) {
            uiLogger.warn("非正常方式进入库存业务功能!");
        }

        return subject;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
    }

    public String getCreator() {
        if(stockOrder == null) return "";
        User u = serviceFor(User.class).get(stockOrder.getOwnerId());
        if(u == null) return "";
        return u.getDisplayName();
    }

    public List<StockOrderItemDto> getItems() {
        if(stockOrder != null) {
            return stockOrder.getItems();
        }
        return null;
    }

    public int getCount() {
        count = serviceFor(StockOrder.class).count(
                new And(new GreaterOrEquals("createdDate", limitDateFrom),
                        new GreaterOrEquals("createdDate", limitDateTo)),
                new Equals("subject_", getSubject().getValue()),
                new Equals("warehouse.id", selectedWarehouse.getId()));
        return count;
    }

    public StockOrderItemDto getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(StockOrderItemDto orderItem) {
        this.orderItem = orderItem;
    }

    public StockOrderItemDto getSelectedOrderItem() {
        return selectedOrderItem;
    }

    public void setSelectedOrderItem(StockOrderItemDto selectedOrderItem) {
        this.selectedOrderItem = selectedOrderItem;
    }

    public List<SelectItem> getCurrencies() {
        List<Currency> currencies = CurrencyConfig.list();
        List<SelectItem> currencyItems = new ArrayList<SelectItem>();
        for(Currency c : currencies) {
            SelectItem i = new SelectItem();
            i.setLabel(CurrencyConfig.format(c));
            i.setValue(c.getCurrencyCode());
            currencyItems.add(i);
        }

        return currencyItems;

    }


    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
    }

    private void loadStockOrder(int goNumber) {
        stockOrder = new StockOrderDto().create();
        if(selectedWarehouse != null) {
            List<StockOrder> oneList = serviceFor(StockOrder.class).list(
                    new Limit(goNumber - 1, 1),
                    new And(new GreaterOrEquals("createdDate", limitDateFrom),
                            new GreaterOrEquals("createdDate", limitDateTo)),
                    new Equals("subject_", getSubject().getValue()),
                    new Equals("warehouse.id", selectedWarehouse.getId()));

            StockOrder s = oneList.get(0);
            stockOrder = DTOs.marshal(StockOrderDto.class, s);
        }
    }

    public void limit() {
        loadStockOrder(goNumber);
    }

    public void new_() {

        editable = true;
    }

    public void modify() {

        editable = true;
    }

    public void delete() {

    }

    public void save() {


        editable = false;
    }

    public void cancel() {


        editable = false;
    }

    public void first() {
        goNumber = 1;
    }

    public void previous() {
        goNumber--;
        if(goNumber < 1) goNumber = 1;
    }

    public void go() {
        if(goNumber < 1) {
            goNumber = 1;
        } else if(goNumber > count) {
            goNumber = count + 1;
        }
    }

    public void next() {
        goNumber++;

        if(goNumber > count) goNumber = count + 1;
    }

    public void last() {
        goNumber = count + 1;
    }


}
