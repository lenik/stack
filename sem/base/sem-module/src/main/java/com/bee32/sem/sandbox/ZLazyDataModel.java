package com.bee32.sem.sandbox;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.web.faces.utils.FacesContextSupport;

public class ZLazyDataModel<E extends Entity<?>, D extends EntityDto<? super E, ?>>
        extends LazyDataModel<D>
        implements Selectable<D> {

    private static final long serialVersionUID = 1L;

    final Class<E> entityClass;
    final Class<D> dtoClass;

    EntityDataModelOptions<E, D> options;
    D selection;
    List<D> loaded;

    public ZLazyDataModel(EntityDataModelOptions<E, D> options) {
        if (options == null)
            throw new NullPointerException("options");
        this.options = options;
        entityClass = options.getEntityClass();
        dtoClass = options.getDtoClass();
    }

    protected CommonDataManager getDataManager() {
        return FacesContextSupport.getBean(CommonDataManager.class);
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        CommonDataManager dataManager = getDataManager();

        Limit limit = new Limit(first, pageSize);
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
        List<E> entities = dataManager.asFor(entityClass).list(limit, criteria, order);

        int dtoSelection = options.getSelection();
        List<D> dtos = DTOs.mrefList(//
                dtoClass, //
                dtoSelection, //
                entities);

        int index = 0;
        for (D dto : dtos)
            dto.set_index(index++);

        return loaded = dtos;
    }

    @Override
    public int getRowCount() {
        int count;
        if (options.isAutoRefreshCount())
            count = executeCountQuery();
        else
            count = super.getRowCount();
        return count;
    }

    public void refreshRowCount() {
        if (!options.autoRefreshCount) {
            int count = executeCountQuery();
            setRowCount(count);
        }
    }

    protected int executeCountQuery() {
        CommonDataManager dataManager = getDataManager();
        ICriteriaElement criteria = options.compose();
        int count = dataManager.asFor(entityClass).count(criteria);
        return count;
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
        rowKey = rowKey.replace("\\-", "_");
        rowKey = rowKey.replace("\\\\", "\\");
        for (D item : loaded) {
            String key = String.valueOf(item.getId());
            if (key.equals(rowKey))
                return item;
        }
        return null;
    }

}
