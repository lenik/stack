package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.sandbox.EntityDataModelOptions;

public class ChooseStockItemDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockItemDialogBean.class);

    String header = "从当前库存中挑选..."; // NLS

    StockQueryOptions queryOptions = new StockQueryOptions(new Date());
    MaterialDto material;

    // List<StockOrderItem> cached;

    public ChooseStockItemDialogBean() {
        super(StockOrderItem.class, StockOrderItemDto.class, 0);
    }

    protected StockItemList query() {
        if (queryOptions == null)
            return null;
        if (material == null || material.isNull())
            return null;

        CommonDataManager dataManager = getBean(CommonDataManager.class);
        List<Material> materials = new ArrayList<Material>();
        Material _material = dataManager.asFor(Material.class).getOrFail(material.getId());
        materials.add(_material);

        IStockQuery query = getBean(IStockQuery.class);
        StockItemList list = query.getActualSummary(materials, queryOptions);
        return list;
    }

    /**
     * Pagination won't work here.
     */
    @Override
    protected <E extends Entity<?>> List<E> listImpl(EntityDataModelOptions<E, ?> options,
            ICriteriaElement... criteriaElements) {
        StockItemList list = query();
        List<E> _list = (List<E>) list.getItems();
        return _list;
    }

    @Override
    protected <E extends Entity<?>> int countImpl(EntityDataModelOptions<E, ?> options,
            ICriteriaElement... criteriaElements) {
        StockItemList list = query();
        return list.getItems().size();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public StockQueryOptions getOptions() {
        return queryOptions;
    }

    public void setOptions(StockQueryOptions options) {
        this.queryOptions = options;
    }

    public static StockQueryOptions buildQueryOptions(Integer warehouseId) {
        if (warehouseId == null || warehouseId == -1)
            return null;

        Calendar endOfToday = Calendar.getInstance();
        endOfToday.set(Calendar.HOUR_OF_DAY, 23);
        endOfToday.set(Calendar.MINUTE, 59);
        endOfToday.set(Calendar.SECOND, 59);
        endOfToday.set(Calendar.MILLISECOND, 999);

        CommonDataManager dataManager = getBean(CommonDataManager.class);
        StockWarehouse warehouse = dataManager.asFor(StockWarehouse.class).getOrFail(warehouseId);

        StockQueryOptions options = new StockQueryOptions(endOfToday.getTime());
        options.setWarehouse(warehouse);
        options.setCBatch(null, true);
        options.setLocation(null, true);
        return options;
    }

}
