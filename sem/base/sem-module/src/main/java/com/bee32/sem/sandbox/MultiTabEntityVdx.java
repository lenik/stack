package com.bee32.sem.sandbox;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleEntityViewBean;

/**
 * View data exchange
 */
public abstract class MultiTabEntityVdx
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected static final int TAB_INDEX = 0;
    protected static final int TAB_FORM = 1;

    int activeTab;
    boolean editing;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    MultiTabEntityVdx(Class<E> entityClass, Class<D> dtoClass, int selection, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, selection, criteriaElements);
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    @Override
    protected void showView(String viewName) {
        switch (viewName) {
        case StandardViews.LIST:
            setActiveTab(TAB_INDEX);
            setEditing(false);
            break;
        case StandardViews.CONTENT:
            setActiveTab(TAB_FORM);
            setEditing(false);
            break;
        case StandardViews.CREATE_FORM:
        case StandardViews.EDIT_FORM:
            setActiveTab(TAB_FORM);
            setEditing(true);
            break;
        }
    }

}
