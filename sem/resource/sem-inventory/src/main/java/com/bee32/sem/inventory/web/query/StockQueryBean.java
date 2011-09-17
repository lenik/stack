package com.bee32.sem.inventory.web.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;

@Component
@Scope("view")
public class StockQueryBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    private Date queryDate = new Date();
    private List<MaterialDto> materialsToQuery = new ArrayList<MaterialDto>();
    private List<String> selectedMaterialsToQuery;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private List<StockOrderItemDto> items;

    private boolean allMaterial;

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List<StockOrderItemDto> getItems() {
        return items;
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

    public boolean isAllMaterial() {
        return allMaterial;
    }

    public void setAllMaterial(boolean allMaterial) {
        this.allMaterial = allMaterial;
    }






    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            List<Material> _materials = serviceFor(Material.class).list(new Like("label", "%" + materialPattern + "%"));

            materials = DTOs.marshalList(MaterialDto.class, _materials);
        }
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
        opts.setCbatch(null, false);
        opts.setLocation(null, false);

        IStockQuery q = getBean(IStockQuery.class);
        StockItemList list = q.getActualSummary(ms, opts);
        items = DTOs.marshalList(StockOrderItemDto.class, list.getItems());
    }

}
