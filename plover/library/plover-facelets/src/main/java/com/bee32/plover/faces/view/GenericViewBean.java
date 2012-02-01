package com.bee32.plover.faces.view;

import javax.free.IllegalUsageException;

import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public abstract class GenericViewBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    protected Class<?> getMajorTypeOfContextPage() {
        String servletPath = ThreadHttpContext.getRequest().getServletPath();
        Class<?> majorType = PageDirectory.whichClass(servletPath);
        if (majorType == null)
            throw new IllegalUsageException("Generic view bean is not applicable for the page: " + servletPath);
        return majorType;
    }

}
