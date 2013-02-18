package com.bee32.sem.sandbox;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;

public class ZLazyDataModel<E extends Entity<?>, D extends EntityDto<? super E, ?>>
        extends LazyDataModelImpl<D> {

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

    public EntityDataModelOptions<E, D> getOptions() {
        return options;
    }

    @Override
    protected boolean isAutoRefreshCount() {
        return options.isAutoRefreshCount();
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<E> entities = loadImpl(first, pageSize, sortField, sortOrder, filters);

        int fmask = options.getFmask();
        List<D> dtos = DTOs.mrefList(options.getDtoClass(), fmask, entities);

        int index = 0;
        for (D dto : dtos)
            dto.set_index(index++);

        postLoad(dtos);

        // if (dtos.size() < pageSize)
        // countCache = dtos.size();
        return pageCache = dtos;
    }

    @Override
    public String getRowKey(D object) {
        Object id = object.getId();
        if (id == null)
            return null; // XXX

        if (id instanceof String) {
            String key = (String) id;
            key = key.replace("\\", "\\\\");
            key = key.replace("_", "\\-");
            return key;
        }
        return id.toString();
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
        List<E> entities = ctx.data.access(options.getEntityClass()).list(limit, criteria, order);
        return entities;
    }

    protected int countImpl() {
        ICriteriaElement criteria = options.compose();
        int count = ctx.data.access(options.getEntityClass()).count(criteria);
        return count;
    }

}
