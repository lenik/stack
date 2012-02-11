package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Collections;
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

    public static final String CONTEXT_BEAN = "EntityPeripheralBean.contextBean";

    protected EntityViewBeanRegistry getViewBeanRegistry() {
        return ctx.bean.getBean(EntityViewBeanRegistry.class);
    }

    protected Class<?> getContextTypeFromPageDirectory() {
        // FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String path = request.getServletPath();
        Class<?> whichClass = PageDirectory.whichClass(path);
        return whichClass;
    }

    protected List<?> getContextSelection() {
        return getContextSelection(new Class<?>[0]);
    }

    protected List<?> getContextSelection(Class<?>... interfaces) {
        EntityViewBean contextBean = getMetadata().getAttribute(CONTEXT_BEAN);
        if (contextBean != null)
            return contextBean.getSelection();
        else
            return Collections.emptyList();
    }

}
