package com.bee32.sem.sandbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class EntityDataModelOptions<E extends Entity<?>, D extends EntityDto<E, ?>> {

    final Class<E> entityClass;
    final Class<D> dtoClass;
    int selection;

    Order order;
    List<Criterion> restrictions = Collections.emptyList();

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass) {
        this(entityClass, dtoClass, 0, null, Collections.<Criterion> emptyList());
    }

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass, int selection, Order order,
            Criterion... restrictions) {
        this(entityClass, dtoClass, selection, order, Arrays.asList(restrictions));
    }

    public EntityDataModelOptions(Class<E> entityClass, Class<D> dtoClass, int selection, Order order,
            List<Criterion> restrictions) {

        if (entityClass == null)
            throw new NullPointerException("entityClass");
        if (dtoClass == null)
            throw new NullPointerException("dtoClass");

        if (restrictions == null)
            restrictions = Collections.emptyList();

        // entityClass = ClassUtil.infer1(getClass(), EntityDataModelOptions.class, 0);
        // dtoClass = ClassUtil.infer1(getClass(), EntityDataModelOptions.class, 1);
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;

        this.selection = selection;
        this.order = order;
        this.restrictions = restrictions;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Criterion> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Criterion... restrictions) {
        setRestrictions(Arrays.asList(restrictions));
    }

    public void setRestrictions(List<Criterion> restrictions) {
        if (restrictions == null)
            throw new NullPointerException("restrictions");
        this.restrictions = restrictions;
    }

}
