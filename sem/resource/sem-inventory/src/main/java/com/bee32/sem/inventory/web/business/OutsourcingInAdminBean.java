package com.bee32.sem.inventory.web.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "OSPI"))
public class OutsourcingInAdminBean extends StockOrderBaseBean {

    private static final long serialVersionUID = 1L;

    private StockOutsourcingDto stockOutsourcing = new StockOutsourcingDto().create();

    private Date limitDateFrom;
    private Date limitDateTo;


    private int goNumber;
    private int count;

    private Date findDateFrom;
    private Date findDateTo;

    private List<StockOutsourcingDto> findedOuts;

    private StockOutsourcingDto selectedOutsourcing;

    private boolean newStatus = false;


    public OutsourcingInAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();
        findDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();
        findDateTo = c.getTime();

        goNumber = 1;

        this.subject = StockOrderSubject.OSP_IN;
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

    public Date getFindDateFrom() {
        return findDateFrom;
    }

    public void setFindDateFrom(Date findDateFrom) {
        this.findDateFrom = findDateFrom;
    }

    public Date getFindDateTo() {
        return findDateTo;
    }

    public void setFindDateTo(Date findDateTo) {
        this.findDateTo = findDateTo;
    }

    public List<StockOutsourcingDto> getFindedOuts() {
        return findedOuts;
    }

    public void setFindedOuts(List<StockOutsourcingDto> findedOuts) {
        this.findedOuts = findedOuts;
    }

    public StockOutsourcingDto getStockOutsourcing() {
        return stockOutsourcing;
    }

    public void setStockOutsourcing(StockOutsourcingDto stockOutsourcing) {
        this.stockOutsourcing = stockOutsourcing;
    }

    public StockOutsourcingDto getSelectedOutsourcing() {
        return selectedOutsourcing;
    }

    public void setSelectedOutsourcing(StockOutsourcingDto selectedOutsourcing) {
        this.selectedOutsourcing = selectedOutsourcing;
    }

    public List<StockOrderItemDto> getFindedOutItems() {
        if(selectedOutsourcing != null)
            return selectedOutsourcing.getOutput().getItems();
        return null;
    }

    public boolean isNewStatus() {
        return newStatus;
    }

    public void setNewStatus(boolean newStatus) {
        this.newStatus = newStatus;
    }









    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
        loadStockLocationTree();
    }

    private void loadStockOrder(int position) {
        //刷新总记录数
        getCount();

        goNumber = position;

        if(position < 1) {
            goNumber = 1;
            position = 1;
        }
        if(goNumber > count) {
            goNumber = count;
            position = count;
        }


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

                StockOutsourcing o = serviceFor(StockOutsourcing.class)
                        .getUnique(new Equals("input.id", stockOrder.getId()));
                if(o != null) {
                    stockOutsourcing = DTOs.marshal(StockOutsourcingDto.class, o);
                }
            }
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

        stockOutsourcing = new StockOutsourcingDto().create();
        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
        //stockOrder.setCreatedDate(new Date());
        editable = true;
        newStatus = true;
    }

    public void modify() {
        if(stockOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
        newStatus = false;
    }

    @Transactional
    public void delete() {
        StockOutsourcing o = serviceFor(StockOutsourcing.class).getUnique(
                new Equals("input.id", stockOrder.getId()));

        try {
            if (o != null) {
                o.setInput(null);
                serviceFor(StockOutsourcing.class).saveOrUpdate(o);
            }
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

        if(stockOrder.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            stockOutsourcing.setInput(stockOrder);
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
            newStatus = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void cancel() {

        loadStockOrder(goNumber);
        editable = false;
        newStatus = false;
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

    public void findOut() {
        List<StockOutsourcing> os = serviceFor(StockOutsourcing.class).list(
                StockCriteria.danglingOutsourcing(findDateFrom, findDateTo));

        findedOuts = DTOs.mrefList(StockOutsourcingDto.class, StockOutsourcingDto.ORDER_ITEMS, os);
    }

    public void chooseFindedOut() {
        stockOutsourcing = reload(selectedOutsourcing);
        findedOuts = null;
    }

    public void clearFindedOuts() {
        findedOuts = null;
    }
}
