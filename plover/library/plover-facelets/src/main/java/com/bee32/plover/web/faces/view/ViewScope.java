package com.bee32.plover.web.faces.view;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;

import com.bee32.plover.inject.scope.AbstractScope;

public class ViewScope
        extends AbstractScope {

    @Override
    protected Map<String, Object> getBeanMap() {
        return null;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        Map<String, Object> viewMap = viewRoot.getViewMap();
        return viewMap;
    }

}
