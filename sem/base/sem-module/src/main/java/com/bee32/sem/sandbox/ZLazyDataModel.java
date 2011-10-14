package com.bee32.sem.sandbox;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.web.faces.utils.FacesContextSupport;

public class ZLazyDataModel<E extends CEntity<?>, D extends CEntityDto<E, ?>>
        extends LazyDataModel<D>
        implements Selectable<D> {

    private static final long serialVersionUID = 1L;

    final Class<E> entityClass;
    final Class<D> dtoClass;
    final ICriteriaElement composition;
    final int dtoSelection;

    D selection;

    public ZLazyDataModel(EntityDataModelOptions<E, D> options) {
        if (options == null)
            throw new NullPointerException("options");
        entityClass = options.getEntityClass();
        dtoClass = options.getDtoClass();
        composition = options.compose();
        dtoSelection = options.getSelection();
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

        CommonDataManager dataManager = FacesContextSupport.getBean(CommonDataManager.class);

        Limit limit = new Limit(first, pageSize);
        Order order = null;
        if(sortField != null) {
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
        List<E> entities = dataManager.asFor(entityClass).list(limit, composition, order);

        List<D> dtos = DTOs.mrefList(//
                dtoClass, //
                dtoSelection, //
                entities);

        return dtos;
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

}
