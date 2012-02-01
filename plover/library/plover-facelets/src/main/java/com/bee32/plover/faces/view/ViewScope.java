package com.bee32.plover.faces.view;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.bee32.plover.inject.scope.AbstractScope;

public class ViewScope
        extends AbstractScope {

    static boolean allowNullFacesContext = false;

    static FacesContext getFacesContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            if (!allowNullFacesContext)
                throw new IllegalStateException("No faces context");
        }
        return facesContext;
    }

    @Override
    protected Map<String, Object> getBeanMap() {
        FacesContext facesContext = getFacesContext();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null)
            throw new IllegalStateException("View root isn't available, yet.");
        Map<String, Object> viewMap = viewRoot.getViewMap();
        return viewMap;
    }

}
