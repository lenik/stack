package com.bee32.sem.sandbox;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityDto;

public class ZLazyDataModel<E extends CEntity<?>, D extends CEntityDto<E, ?>>
        extends LazyDataModel<D>
        implements Selectable<D> {

    private static final long serialVersionUID = 1L;

    final CommonDataManager dataManager;
    final EntityDataModelOptions<E, D> options;
    final Class<E> entityClass;

    D selection;

    public ZLazyDataModel(CommonDataManager dataManager, EntityDataModelOptions<E, D> options) {
        if (dataManager == null)
            throw new NullPointerException("dataManager");
        if (options == null)
            throw new NullPointerException("options");
        this.dataManager = dataManager;
        this.options = options;
        this.entityClass = options.getEntityClass();
    }

    @Override
    public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

        Limit limit = new Limit(first, pageSize);
        ICriteriaElement composition = options.compose();
        List<E> entities = dataManager.asFor(entityClass).list(limit, composition);

        List<D> dtos = DTOs.marshalList(//
                options.getDtoClass(), //
                options.getSelection(), //
                entities, true);

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

}
