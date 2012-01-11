package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.tx.entity.StockTaking;
import com.bee32.sem.inventory.util.StockCriteria;

@ForEntity(StockTaking.class)
public class StocktakingAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;
    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    protected StockOrderDto stockOrder = new StockOrderDto().create().ref();

    protected boolean editable = false;

    private Date limitDateFrom;
    private Date limitDateTo;

    private Date queryDate = new Date();

    private int goNumber;
    private int count;

    private List<MaterialDto> materialsToQuery = new ArrayList<MaterialDto>();
    private List<String> selectedMaterialsToQuery;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private boolean allMaterial;


    public StocktakingAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;

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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
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

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
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

    public void setSelectedMaterialsToQuery(List<String> selectedMaterialsToQuery) {
        this.selectedMaterialsToQuery = selectedMaterialsToQuery;
    }

    public boolean isAllMaterial() {
        return allMaterial;
    }

    public void setAllMaterial(boolean allMaterial) {
        this.allMaterial = allMaterial;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }





    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
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
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
                    CommonCriteria.createdBetweenEx(limitDateFrom,limitDateTo), //
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
        //stockOrder.setCreatedDate(new Date());
        editable = true;
    }

    public void modify() {
        if(stockOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

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

        if(stockOrder.getId() == null) {
            //新增
            goNumber = count + 1;
        }


        try {
            StockOrder _order = stockOrder.unmarshal();
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

    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            List<Material> _materials = serviceFor(Material.class).list(new Like("label", "%" + materialPattern + "%"));

            materials = DTOs.mrefList(MaterialDto.class, _materials);
        }

        selectedMaterial = null;
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
}
