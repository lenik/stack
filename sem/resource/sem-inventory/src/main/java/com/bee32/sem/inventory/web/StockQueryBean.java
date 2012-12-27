package com.bee32.sem.inventory.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.dto.StockItemListDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.material.dto.StockWarehouseDto;

@ForEntity(StockInventory.class)
public class StockQueryBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    StockDictsBean dicts;

    Integer selectedWarehouseId = -1;
    Date queryDate = new Date();

    boolean verified;

    StockItemListDto resultList = new StockItemListDto().create();
    StockOrderItemDto selectedItem;

    StockOrderItemDto detailItem;
    int orderIndex = -1;
    int orderItemIndex = -1;

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public void copyResult(StockQueryResult result) {
        if (result == null)
            throw new NullPointerException("resultList");
        this.resultList = new StockOrderDto().marshal(result);
        uiLogger.info("查询完成。");
    }

    public StockItemListDto getResultList() {
        return resultList;
    }

    public StockOrderItemDto getSelectedItem() {
        return selectedItem;
    }

    public BigDecimal getTotalQuantity() {
        BigDecimal totalQuantity = new BigDecimal(0);
        if(resultList != null) {
            List<StockOrderItemDto> resultItems = resultList.getItems();
            for(StockOrderItemDto item : resultItems) {
                totalQuantity = totalQuantity.add(item.getQuantity());
            }
        }

        return totalQuantity;
    }

    public void setSelectedItem(StockOrderItemDto selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<StockOrderItemDto> getDetails() {
        if (selectedItem == null)
            return new ArrayList<StockOrderItemDto>();

        StockQueryOptions opts = new StockQueryOptions(queryDate, true);
        opts.setShowAll(true); // 数量为0也显示
        opts.setWarehouse(getSelectedWarehouseId());
        opts.setBatchArray(selectedItem.getBatchArray(), true);
        opts.setPrice(selectedItem.getPrice(), true);
        opts.setLocation(selectedItem.getLocation().getId(), true);

        List<StockOrderItem> details = DATA(StockOrderItem.class).list( //
                StockCriteria.inOutDetail( //
                        queryDate, //
                        selectedItem.getMaterial().getId(), //
                        opts));

        return DTOs.marshalList(StockOrderItemDto.class, details);
    }

    public StockOrderItemDto getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(StockOrderItemDto detailItem) {
        this.detailItem = detailItem;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public int getOrderItemIndex() {
        return orderItemIndex;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void findOrderSn() {
        if (detailItem != null && detailItem.getId() != null) {
            StockOrderSubject subject = detailItem.getParent().getSubject();

            Calendar c = Calendar.getInstance();
            c.setTime(detailItem.getParent().getCreatedDate());
            // 取这个月的第一天
            c.set(Calendar.DAY_OF_MONTH, 1);
            Date limitDateFrom = c.getTime();

            // 最这个月的最后一天
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            Date limitDateTo = c.getTime();

            List<StockOrder> orders = DATA(StockOrder.class).list( //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(subject), //
                    new Equals("warehouse.id", detailItem.getParent().getWarehouse().getId()), //
                    Order.asc("id"));
            orderIndex = -1;
            orderItemIndex = -1;

            int index = 0;
            for (StockOrder o : orders) {
                index++;

                if (o.getId().equals(detailItem.getParent().getId())) {
                    int itemIndex = 0;
                    for (StockOrderItem i : o.getItems()) {
                        itemIndex++;
                        if (i.getId().equals(detailItem.getId())) {
                            orderItemIndex = itemIndex;
                            break;
                        }
                    }

                    orderIndex = index;
                    break;
                }
            }
        }
    }

}
