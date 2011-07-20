package com.bee32.sem.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.dict.NameDictDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.FacesContextSupport2;

public class UIHelper
        extends FacesContextSupport2 {

    public static List<SelectItem> selectItemsFromDict(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    public static <E extends Entity<?>, D extends EntityDto<E, ?>> //
    LazyDataModel<D> buildLazyDataModel(final EntityDataModelOptions<E, D> options) {
        if (options == null)
            throw new NullPointerException("options");

        final Class<E> entityClass = options.getEntityClass();
        final Class<D> dtoClass = options.getDtoClass();

        final int selection = options.getSelection();
        final ICriteriaElement composition = options.compose();

        return new LazyDataModel<D>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<D> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                    Map<String, String> filters) {

                Limit limit = new Limit(first, pageSize);
                List<E> entities = serviceFor(entityClass).list(limit, composition);

                List<D> dtos = DTOs.marshalList(dtoClass, selection, entities);

                return dtos;
            }
        };
    }

}
