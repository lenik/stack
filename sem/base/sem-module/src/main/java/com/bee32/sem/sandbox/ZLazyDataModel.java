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

    EntityDataModelOptions<E, D> options;
    D selection;
    List<D> loaded;
    Integer lastQueriedCount;

    public ZLazyDataModel(EntityDataModelOptions<E, D> options) {
        if (options == null)
            throw new NullPointerException("options");
        this.options = options;
    }

    protected CommonDataManager getDataManager() {
        return FacesContextSupport.getBean(CommonDataManager.class);
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
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
        List<D> dtos = _listDtos(limit, criteria, order);

        // if (dtos.size() < pageSize)
        // lastQueriedCount = dtos.size();
        return loaded = dtos;
    }

    public List<D> listDtos() {
        ICriteriaElement criteria = options.compose();
        return _listDtos(criteria);
    }

    public List<D> _listDtos(ICriteriaElement... criteriaElements) {
        CommonDataManager dataManager = getDataManager();

        List<E> entities = dataManager.asFor(options.getEntityClass()).list(criteriaElements);

        int dtoSelection = options.getSelection();
        List<D> dtos = DTOs.mrefList(//
                options.getDtoClass(), //
                dtoSelection, //
                entities);

        int index = 0;
        for (D dto : dtos)
            dto.set_index(index++);

        return dtos;
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
        if (options.isAutoRefreshCount() || lastQueriedCount == null)
            count = executeCountQuery();
        else {
            count = lastQueriedCount;
        }
        return count;
    }

    @Override
    public void setRowCount(int rowCount) {
        lastQueriedCount = rowCount;
    }

    public void refreshRowCount() {
        if (!options.autoRefreshCount)
            executeCountQuery();
    }

    protected int executeCountQuery() {
        CommonDataManager dataManager = getDataManager();
        ICriteriaElement criteria = options.compose();
        lastQueriedCount = dataManager.asFor(options.getEntityClass()).count(criteria);
        return lastQueriedCount;
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
