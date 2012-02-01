package com.bee32.plover.faces;

import java.util.Map;

import javax.faces.context.FacesContext;

public class FaceletExceptionContext {

    FacesContext facesContext;
    Map<String, Object> requestMap;

    public FaceletExceptionContext(FacesContext facesContext) {
        if (facesContext == null)
            throw new NullPointerException("facesContext");
        this.facesContext = facesContext;

        requestMap = facesContext.getExternalContext().getRequestMap();
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

}
