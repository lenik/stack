package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.orm.annotation.EntityTypePattern;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public abstract class EntityPeripheralBean
        extends FacesContextSupport2
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Class<?> getContextType() {
        // FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String path = request.getServletPath();
        Class<?> whichClass = PageDirectory.whichClass(path);
        return whichClass;
    }

    protected EntityViewBeanRegistry getViewBeanRegistry() {
        return getBean(EntityViewBeanRegistry.class);
    }

    /**
     * @return <code>null</code> if there is no view bean which is annotated with the corresponding
     *         &#64;{@link ForEntity}.
     */
    public List<Class<? extends EntityViewBean>> getContextViewBeanTypes() {
        Class<?> mainType = getContextType();
        if (mainType == null)
            return null;
        if (!Entity.class.isAssignableFrom(mainType))
            return null;

        @SuppressWarnings("unchecked")
        Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) mainType;

        EntityTypePattern pattern = new EntityTypePattern(entityType);

        return getViewBeanRegistry().getViewBeanClasses(pattern);
    }

    /**
     * @return <code>null</code> if there is no view bean which is annotated with the corresponding
     *         &#64;{@link ForEntity}.
     */
    public List<? extends EntityViewBean> getContextViewBeans() {
        return getContextViewBeans(new Class<?>[0]);
    }

    public List<? extends EntityViewBean> getContextViewBeans(Class<?>... interfaces) {
        List<Class<? extends EntityViewBean>> viewBeanTypes = getContextViewBeanTypes();
        if (viewBeanTypes == null)
            return null;

        List<EntityViewBean> viewBeans = new ArrayList<EntityViewBean>(viewBeanTypes.size());
        for (Class<? extends EntityViewBean> viewBeanType : viewBeanTypes) {
            if (interfaces != null && interfaces.length != 0) {
                boolean interesting = true;
                for (Class<?> iface : interfaces)
                    if (!iface.isAssignableFrom(viewBeanType)) {
                        interesting = false;
                        break;
                    }
                if (!interesting)
                    continue;
            }
            EntityViewBean viewBean = getBean(viewBeanType);
            viewBeans.add(viewBean);
        }
        return viewBeans;
    }

}
