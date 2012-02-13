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
public abstract class MultiTabEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected static final int TAB_INDEX = 0;
    protected static final int TAB_FORM = 1;

    int tabIndex;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    MultiTabEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    protected void showView(String viewName) {
        super.showView(viewName);
        switch (viewName) {
        case StandardViews.LIST:
            setTabIndex(TAB_INDEX);
            break;
        case StandardViews.CONTENT:
        case StandardViews.CREATE_FORM:
        case StandardViews.EDIT_FORM:
            setTabIndex(TAB_FORM);
            break;
        }
    }

}
