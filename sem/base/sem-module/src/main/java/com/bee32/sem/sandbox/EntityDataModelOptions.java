package com.bee32.sem.sandbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.free.Caller;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityDto;

public class EntityDataModelOptions<E extends CEntity<?>, D extends CEntityDto<? super E, ?>> {

    final Class<?> creator = Caller.getCallerClass(1);
    final Class<E> entityClass;
    final Class<D> dtoClass;
    int selection;

    List<ICriteriaElement> criteriaElements = Collections.emptyList();

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass) {
        this(entityClass, dtoClass, 0, Collections.<ICriteriaElement> emptyList());
    }

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass, int selection,
            ICriteriaElement... criteriaElements) {
        this(entityClass, dtoClass, selection, Arrays.asList(criteriaElements));
    }

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass, int selection,
            List<ICriteriaElement> criteriaElements) {

        if (entityClass == null)
            throw new NullPointerException("entityClass");
        if (dtoClass == null)
            throw new NullPointerException("dtoClass");

        if (criteriaElements == null)
            criteriaElements = Collections.emptyList();

        // entityClass = ClassUtil.infer1(getClass(), EntityDataModelOptions.class, 0);
        // dtoClass = ClassUtil.infer1(getClass(), EntityDataModelOptions.class, 1);
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;

        this.selection = selection;
        this.criteriaElements = criteriaElements;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public Class<D> getDtoClass() {
        return dtoClass;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public List<ICriteriaElement> getCriteriaElements() {
        return criteriaElements;
    }

    public void setCriteriaElements(ICriteriaElement... criteriaElements) {
        setCriteriaElements(Arrays.asList(criteriaElements));
    }

    public void setCriteriaElements(List<ICriteriaElement> criteriaElements) {
        if (criteriaElements == null)
            throw new NullPointerException("criteriaElements");
        this.criteriaElements = criteriaElements;
    }

    public ICriteriaElement compose() {
        if (criteriaElements.isEmpty())
            return null;
        else if (criteriaElements.size() == 1)
            return criteriaElements.get(0);
        else
            return new CriteriaComposite(criteriaElements);
    }

}
