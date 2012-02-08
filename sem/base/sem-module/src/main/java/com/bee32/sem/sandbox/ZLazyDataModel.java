package com.bee32.sem.sandbox;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.faces.utils.FacesContextUtils;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;

public class ZLazyDataModel<E extends Entity<?>, D extends EntityDto<? super E, ?>>
        extends LazyDataModel<D>
        implements Selectable<D> {

    private static final long serialVersionUID = 1L;

    protected final EntityDataModelOptions<E, D> options;
    D selection;
    List<D> pageCache;
    Integer countCache;

    public ZLazyDataModel(EntityDataModelOptions<E, D> options) {
        if (options == null)
            throw new NullPointerException("options");
        this.options = options;
    }

    protected CommonDataManager getDataManager() {
        return FacesContextUtils.getBean(CommonDataManager.class);
    }

    public EntityDataModelOptions<E, D> getOptions() {
        return options;
    }

    /**
     * Load a single row.
     *
     * @param rowIndex
     *            0-based row index.
     * @return <code>null</code> if no row available at the specified row index.
     */
    public final D load(int rowIndex) {
        return load(rowIndex, null, SortOrder.ASCENDING);
    }

    /**
     * Load a single row.
     *
     * @param rowIndex
     *            0-based row index.
     * @return <code>null</code> if no row available at the specified row index.
     */
    public final D load(int rowIndex, String sortField, SortOrder sortOrder) {
        if (rowIndex < 0)
            throw new IllegalArgumentException("rowIndex can't be negative.");
        List<D> list = load(rowIndex, 1, sortField, sortOrder, Collections.<String, String> emptyMap());
        if (list.isEmpty())
            return null;
        // assert list.size() == 1;
        return list.get(0);
    }

    public List<D> loadAll() {
        return loadAll(null, SortOrder.ASCENDING);
    }

    public List<D> loadAll(String sortField, SortOrder sortOrder) {
        return load(-1, -1, sortField, sortOrder, Collections.<String, String> emptyMap());
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<E> entities = loadImpl(first, pageSize, sortField, sortOrder, filters);

        int fmask = options.getFmask();
        List<D> dtos = DTOs.mrefList(options.getDtoClass(), fmask, entities);

        int index = 0;
        for (D dto : dtos)
            dto.set_index(index++);

        // if (dtos.size() < pageSize)
        // countCache = dtos.size();
        return pageCache = dtos;
    }

    @Override
    public int getRowIndex() {
        return super.getRowIndex();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (getPageSize() == 0)
            return; // throw new IllegalStateException("pageSize isn't initialized.");
        super.setRowIndex(rowIndex);
    }

    @Override
    public int getRowCount() {
        int count;
        if (options.isAutoRefreshCount() || countCache == null)
            count = cachedCount();
        else {
            count = countCache;
        }
        return count;
    }

    @Override
    public void setRowCount(int rowCount) {
        countCache = rowCount;
    }

    public void refreshRowCount() {
        if (!options.isAutoRefreshCount())
            cachedCount();
    }

    protected int cachedCount() {
        countCache = count();
        return countCache;
    }

    protected int count() {
        return countImpl();
    }

    @Override
    public D getSelection() {
        return selection;
    }

    @Override
    public void setSelection(D selection) {
        this.selection = selection;
    }

    @Override
    public boolean isSelected() {
        return selection != null;
    }

    @Override
    public void deselect() {
        selection = null;
    }

    @Override
    public Object getRowKey(D object) {
        Object id = object.getId();
        if (id instanceof String) {
            String key = (String) id;
            key = key.replace("\\", "\\\\");
            key = key.replace("_", "\\-");
            return key;
        }
        return id;
    }

    @Override
    public D getRowData(String rowKey) {
        if (pageCache == null) {
            new FacesUILogger(false).warn("Data not loaded!");
            return null;
        }
        rowKey = rowKey.replace("\\-", "_");
        rowKey = rowKey.replace("\\\\", "\\");
        for (D item : pageCache) {
            String key = String.valueOf(item.getId());
            if (key.equals(rowKey))
                return item;
        }
        return null;
    }

    /**
     * @param first
     *            0-based row index.
     */
    protected List<E> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        Limit limit;
        if (first == -1 && pageSize == -1)
            limit = null;
        else
            limit = new Limit(first, pageSize);

        Order order = null;
        if (sortField != null) {
            switch (sortOrder) {
            case ASCENDING:
                order = Order.asc(sortField);
                break;
            case DESCENDING:
                order = Order.desc(sortField);
                break;
            default:
                break;
            }
        }

        ICriteriaElement criteria = options.compose();
        CommonDataManager dataManager = getDataManager();
        List<E> entities = dataManager.asFor(options.getEntityClass()).list(limit, criteria, order);
        return entities;
    }

    protected int countImpl() {
        ICriteriaElement criteria = options.compose();
        CommonDataManager dataManager = getDataManager();
        int count = dataManager.asFor(options.getEntityClass()).count(criteria);
        return count;
    }

}
