package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IdUtils;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public class StockQueryDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(StockQueryDialogBean.class);

    String header = "从当前库存中挑选..."; // NLS

    StockQueryOptions queryOptions = new StockQueryOptions(new Date(), true);

    boolean all;
    List<MaterialDto> materials = new ArrayList<MaterialDto>();
    Long selectedMaterialId;
    MaterialDto chosenMaterial;

    StockItemList resultList;

    // List<StockOrderItem> cached;

    public StockQueryDialogBean() {
        super(StockOrderItem.class, StockOrderItemDto.class, 0);
        setTabIndex(1); // Results-Tab
    }

    /**
     * Pagination won't work here.
     */
    @Override
    protected <E extends Entity<?>> List<E> listImpl(EntityDataModelOptions<E, ?> options,
            ICriteriaElement... criteriaElements) {
        StockItemList list = query();
        if (list == null)
            return Collections.emptyList();
        List<E> _list = (List<E>) list.getItems();
        return _list;
    }

    @Override
    protected <E extends Entity<?>> int countImpl(EntityDataModelOptions<E, ?> options,
            ICriteriaElement... criteriaElements) {
        StockItemList list = query();
        if (list == null)
            return 0;
        return list.getItems().size();
    }

    protected StockItemList query() {
        if (queryOptions == null) {
            uiLogger.warn("queryOptions is null");
            return null;
        }

        List<Long> materialIds;
        if (all)
            materialIds = null;
        else
            materialIds = IdUtils.<Long> getDtoIdList(materials);

        IStockQuery query = getBean(IStockQuery.class);
        resultList = query.getActualSummary(materialIds, queryOptions);
        return resultList;
    }

    public StockItemList getResultList() {
        return resultList;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public StockQueryOptions getQueryOptions() {
        return queryOptions;
    }

    public void setQueryOptions(StockQueryOptions options) {
        // this.queryOptions = options;
        this.queryOptions.populate(options);
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
    }

    public MaterialDto getSingleMaterial() {
        if (materials.isEmpty())
            return null;
        else
            return materials.get(0);
    }

    public void setSingleMaterial(MaterialDto material) {
        materials.clear();
        if (material != null && !material.isNull())
            materials.add(material);
    }

    public List<SelectItem> getMaterialSelectItems() {
        return UIHelper.selectItems(materials);
    }

    public Long getSelectedMaterialId() {
        return selectedMaterialId;
    }

    public void setSelectedMaterialId(Long selectedMaterialId) {
        this.selectedMaterialId = selectedMaterialId;
    }

    public void removeSelectedMaterial() {
        if (materials == null)
            return; // warn?
        if (selectedMaterialId == null)
            return; // warn?
        Iterator<MaterialDto> iterator = materials.iterator();
        while (iterator.hasNext()) {
            MaterialDto material = iterator.next();
            if (selectedMaterialId.equals(material.getId()))
                iterator.remove();
        }
        selectedMaterialId = null;
    }

    public MaterialDto getChosenMaterial() {
        return chosenMaterial;
    }

    public void setChosenMaterial(MaterialDto chosenMaterial) {
        this.chosenMaterial = chosenMaterial;
    }

    public void addMaterial() {
        if (materials == null)
            materials = new ArrayList<MaterialDto>();
        if (!materials.contains(chosenMaterial))
            materials.add(chosenMaterial);
    }

    public static StockQueryOptions buildQueryOptions(Integer warehouseId) {
        if (warehouseId == null || warehouseId == -1)
            return null;

        StockQueryOptions options = new StockQueryOptions(new Date(), true);
        options.setVerifiedOnly(false); // XXX for test.
        options.setWarehouse(warehouseId);
        options.setCBatch(null, true);
        options.setLocation(null, true);
        return options;
    }

}
