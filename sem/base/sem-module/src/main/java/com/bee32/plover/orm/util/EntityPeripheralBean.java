package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.servlet.util.ThreadHttpContext;

/**
 * The context view beans are determined as follows:
 * <ol>
 * <li>from view-metadata.viewBeans: find the first entity-viewbean.
 * <li>by request-URL and page-directory.
 * </ol>
 */
public abstract class EntityPeripheralBean
        extends DataViewBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected EntityViewBeanRegistry getViewBeanRegistry() {
        return getBean(EntityViewBeanRegistry.class);
    }

    protected Class<?> getContextTypeFromPageDirectory() {
        // FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String path = request.getServletPath();
        Class<?> whichClass = PageDirectory.whichClass(path);
        return whichClass;
    }

    protected List<?> getSelection() {
        return getSelection(new Class<?>[0]);
    }

    protected List<?> getSelection(Class<?>... interfaces) {
        List<Object> all = new ArrayList<Object>();
        List<?> viewBeans = getMetadata().getViewBeans(EntityViewBean.class);
        for (Object _viewBean : viewBeans) {
            EntityViewBean viewBean = (EntityViewBean) _viewBean;
            List<?> selection = viewBean.getSelection(interfaces);
            all.addAll(selection);
        }
        return all;
    }

}
