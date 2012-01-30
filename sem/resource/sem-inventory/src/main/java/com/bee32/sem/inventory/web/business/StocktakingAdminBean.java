package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.tx.dto.StockTakingDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(StockTaking.class)
public class StocktakingAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;
    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    protected StockOrderDto stockOrder = new StockOrderDto().create().ref();

    protected boolean editable = false;

    private Date queryDate = new Date();

    private List<MaterialDto> materialsToQuery = new ArrayList<MaterialDto>();
    private List<String> selectedMaterialsToQuery;

    public StocktakingAdminBean() {
        super(StockTaking.class, StockTakingDto.class, 0);
        subject = StockOrderSubject.STKD;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        this.subject = subject;
    }

    public List<SelectItem> getStockWarehouses() {
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.mrefList(StockWarehouseDto.class, stockWarehouses);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (StockWarehouseDto stockWarehouseDto : stockWarehouseDtos) {
            String label = stockWarehouseDto.getName();
            SelectItem item = new SelectItem(stockWarehouseDto.getId(), label);
            items.add(item);
        }

        return items;
    }

    public String getCreator() {
        if (stockOrder == null)
            return "";
        else
            return stockOrder.getOwnerDisplayName();
    }

    public List<StockOrderItemDto> getItems() {
        if (stockOrder == null)
            return null;
        return stockOrder.getItems();
    }

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List<SelectItem> getMaterialsToQuery() {
        List<SelectItem> ms = new ArrayList<SelectItem>();
        for (MaterialDto m : materialsToQuery) {
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

    public void setSelectedMaterialsToQuery(List<String> selectedMaterialsToQuery) {
        this.selectedMaterialsToQuery = selectedMaterialsToQuery;
    }

    private void loadStockOrder(int position) {
        stockOrder = new StockOrderDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
//                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
//                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()), //
                    Order.asc("id"));

            if (firstOrder != null)
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);
        }
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

    public void save1() {
        stockOrder.setWarehouse(selectedWarehouse);

        if (stockOrder.getId() == null) {
            // 新增
//            goNumber = count + 1;
        }

        try {
            StockOrder _order = stockOrder.unmarshal();
            serviceFor(StockOrder.class).save(_order);
            uiLogger.info("保存成功");
//            loadStockOrder(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void addMaterial() {
//        for (MaterialDto m : materialsToQuery) {
//            if (selectedMaterial.getId().equals(m.getId())) {
//                return;
//            }
//        }
//        materialsToQuery.add(selectedMaterial);
    }

    private void removeMaterialDtoWithIdFromList(List<MaterialDto> ms, Long id) {
        for (MaterialDto m : ms) {
            if (m.getId().equals(id)) {
                ms.remove(m);
                return;
            }
        }
    }

    public void removeMaterialsToQuery() {
        if (selectedMaterialsToQuery != null) {
            for (String materialId : selectedMaterialsToQuery) {
                removeMaterialDtoWithIdFromList(materialsToQuery, Long.parseLong(materialId));
            }
        }
    }
}
