package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;

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


    private StockOrderDto stockOrder;

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

    public List<StockOrderItemDto> getItems() {
        return null;
    }


    public void onSwChange(AjaxBehaviorEvent e) {

    }

    public void limit() {

    }

    public void new_() {

    }

    public void modify() {

    }

    public void delete() {

    }

    public void save() {

    }

    public void cancel() {

    }

    public void first() {

    }

    public void previous() {

    }

    public void go() {

    }

    public void next() {

    }

    public void last() {

    }


}
