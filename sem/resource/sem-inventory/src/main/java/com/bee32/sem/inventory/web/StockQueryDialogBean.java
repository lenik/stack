package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.free.Nullables;

import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IdUtils;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

public class StockQueryDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(StockQueryDialogBean.class);

    static final int TAB_OPTIONS = 0;
    static final int TAB_RESULT = 1;

    String header = "从当前库存中挑选..."; // NLS

    StockQueryOptions queryOptions;

    boolean queryAllMaterials;
    List<MaterialDto> materials = new ArrayList<MaterialDto>();
    Long selectedMaterialId;
    MaterialDto chosenMaterial;

    boolean autoQuery;

    StockOrder queryCache = new StockOrder();
    boolean cacheValid;
    List<MaterialDto> cacheForMaterials;
    boolean cacheForAll;

    // List<StockOrderItem> cached;

    public StockQueryDialogBean() {
        super(StockOrderItem.class, StockOrderItemDto.class, 0);
        setTabIndex(TAB_OPTIONS);
        queryOptions = new StockQueryOptions(new Date(), true);
        queryOptions.setWarehouse(null, true);
        queryOptions.setCBatch(null, true);
        queryOptions.setLocation(null, true);
    }

    @Override
    protected <E extends Entity<?>> List<E> loadImpl(SevbLazyDataModel<E, ?> def, int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, String> filters) {
        StockOrder sumOrder;
        if (autoQuery)
            sumOrder = cachedQuery();
        else
            sumOrder = queryCache; // cachedQuery();

        List<E> list = (List<E>) sumOrder.getItems();

        if (first < 0)
            first = 0;
        int end = first + pageSize;
        if (end > list.size())
            end = list.size();
        return list.subList(first, end);
    }

    @Override
    protected <E extends Entity<?>> int countImpl(SevbLazyDataModel<E, ?> def) {
        StockOrder list;
        if (autoQuery)
            list = cachedQuery();
        else
            list = queryCache;
        int count = list.getItems().size();
        return count;
    }

    public StockOrder getResultList() {
        return cachedQuery();
    }

    public synchronized StockOrder cachedQuery() {
        if (!cacheValid)
            query();
        return queryCache;
    }

    public synchronized StockOrder query() {
        queryCache = queryImpl();
        cacheValid = true;
        cacheForAll = queryAllMaterials;
        cacheForMaterials = new ArrayList<MaterialDto>(materials);
        refreshRowCount();
        setTabIndex(TAB_RESULT);
        return queryCache;
    }

    protected synchronized StockOrder queryImpl() {
        if (queryOptions == null) {
            uiLogger.warn("queryOptions is null");
            return null;
        }

        List<Long> materialIds;
        if (queryAllMaterials)
            materialIds = null;
        else
            materialIds = IdUtils.<Long> getDtoIdList(materials);

        IStockQuery query = ctx.getBean(IStockQuery.class);
        StockOrder sumOrder = query.getActualSummary(materialIds, queryOptions);
        return sumOrder;
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

    public boolean isAutoQuery() {
        return autoQuery;
    }

    public void setAutoQuery(boolean autoQuery) {
        this.autoQuery = autoQuery;
    }

    public boolean isQueryAllMaterials() {
        return queryAllMaterials;
    }

    public void setQueryAllMaterials(boolean queryAllMaterials) {
        this.queryAllMaterials = queryAllMaterials;
        if (queryAllMaterials != cacheForAll)
            cacheValid = false;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        if (materials == null)
            throw new NullPointerException("materials");
        this.materials = materials;
        setQueryAllMaterials(false);
        if (cacheValid && !Nullables.equals(cacheForMaterials, materials))
            cacheValid = false;
    }

    public final MaterialDto getSingleMaterial() {
        if (materials.isEmpty())
            return null;
        else
            return materials.get(0);
    }

    public final void setSingleMaterial(MaterialDto material) {
        List<MaterialDto> materials = new ArrayList<MaterialDto>();
        materials.clear();
        if (material != null && !material.isNull())
            materials.add(material);
        setMaterials(materials);
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
        options.setWarehouse(warehouseId);
        options.setCBatch(null, true);
        options.setLocation(null, true);
        return options;
    }

}
