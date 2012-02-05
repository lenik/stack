package com.bee32.sem.inventory.web.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(StockInventory.class)
public class StockQueryBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    private Date queryDate = new Date();
    private List<MaterialDto> materialsToQuery = new ArrayList<MaterialDto>();
    private List<String> selectedMaterialsToQuery;

    private MaterialDto selectedMaterial;

    private List<StockOrderItemDto> items;
    private StockOrderItemDto selectedItem;

    private StockOrderItemDto detailItem;

    private boolean allMaterial;

    private int orderIndex = -1;
    private int orderItemIndex = -1;

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public List<StockOrderItemDto> getItems() {
        return items;
    }

    public StockOrderItemDto getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(StockOrderItemDto selectedItem) {
        this.selectedItem = selectedItem;
    }

    public StockOrderItemDto getDetailItem() {
        return detailItem;
    }

    public void setDetailItem(StockOrderItemDto detailItem) {
        this.detailItem = detailItem;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List<SelectItem> getMaterialsToQuery() {
        List<SelectItem> ms = new ArrayList<SelectItem>();
        for(MaterialDto m : materialsToQuery) {
            SelectItem i = new SelectItem();
            i.setLabel(m.getLabel());
            i.setValue(m.getId());

            ms.add(i);
        }
        return ms;
    }

    public List<String> getSelectedMaterialsToQuery() {
        return selectedMaterialsToQuery;
    }

    public void setSelectedMaterialsToQuery(
            List<String> selectedMaterialsToQuery) {
        this.selectedMaterialsToQuery = selectedMaterialsToQuery;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public boolean isAllMaterial() {
        return allMaterial;
    }

    public void setAllMaterial(boolean allMaterial) {
        this.allMaterial = allMaterial;
    }

    public List<StockOrderItemDto> getDetails() {
        if (selectedItem != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(queryDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);

            StockQueryOptions opts = new StockQueryOptions(c.getTime());
            opts.setWarehouse(selectedWarehouse.unmarshal());
            opts.setCBatch(selectedItem.getCBatch(), true);
            opts.setLocation(selectedItem.getLocation().unmarshal(), true);

            List<StockOrderItem> details = serviceFor(StockOrderItem.class).list( //
                    StockCriteria.inOutDetail( //
                            queryDate, //
                            selectedItem.getMaterial().getId(), //
                            opts));

            return DTOs.marshalList(StockOrderItemDto.class, details);
        }

        return new ArrayList<StockOrderItemDto>();
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public int getOrderItemIndex() {
        return orderItemIndex;
    }


    public void addMaterial() {
        for(MaterialDto m : materialsToQuery) {
            if(selectedMaterial.getId().equals(m.getId())) {
                return;
            }
        }
        materialsToQuery.add(selectedMaterial);
    }

    private void removeMaterialDtoWithIdFromList(List<MaterialDto> ms, Long id) {
        for(MaterialDto m : ms) {
            if(m.getId().equals(id)) {
                ms.remove(m);
                return;
            }
        }
    }

    public void removeMaterialsToQuery() {
        if(selectedMaterialsToQuery != null) {
            for(String materialId : selectedMaterialsToQuery) {
                removeMaterialDtoWithIdFromList(materialsToQuery, Long.parseLong(materialId));
            }
        }
    }

    public void query() {
        if(selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择仓库!");
            return;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(queryDate);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        StockWarehouse w = selectedWarehouse.unmarshal();
        List<Material> ms = null;
        if(!allMaterial) {
            if(materialsToQuery.size() <= 0) {
                uiLogger.warn("请选择需要查询是物料!");
                return;
            }

            ms = new ArrayList<Material>();
            for(MaterialDto m : materialsToQuery) {
                ms.add(m.unmarshal());
            }
        }

        StockQueryOptions opts = new StockQueryOptions(c.getTime());
        opts.setWarehouse(w);
        opts.setCBatch(null, true);
        opts.setLocation(null, true);

        IStockQuery q = getBean(IStockQuery.class);
        StockItemList list = q.getActualSummary(ms, opts);
        items = DTOs.marshalList(StockOrderItemDto.class, list.getItems());
    }

    public void findOrderSn() {
        if(detailItem != null && detailItem.getId() != null) {
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

            List<StockOrder> orders = serviceFor(StockOrder.class).list( //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(subject), //
                    new Equals("warehouse.id", detailItem.getParent().getWarehouse().getId()), //
                    Order.asc("id"));
            orderIndex = -1;
            orderItemIndex = -1;


            int index = 0;
            for(StockOrder o : orders) {
                index ++;

                if(o.getId().equals(detailItem.getParent().getId())) {
                    int itemIndex = 0;
                    for(StockOrderItem i : o.getItems()) {
                        itemIndex ++;
                        if(i.getId().equals(detailItem.getId())) {
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
