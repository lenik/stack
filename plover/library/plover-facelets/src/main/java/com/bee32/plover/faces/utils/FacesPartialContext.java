package com.bee32.plover.faces.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.plover.arch.util.ApplicationContextPartialContext;
import com.bee32.plover.arch.util.IPartialContext;

public class FacesPartialContext
        extends ApplicationContextPartialContext
        implements IPartialContext {

    public final FacesUILogger uiLogger = new FacesUILogger(false);
    public final FacesUILogger uiHtmlLogger = new FacesUILogger(true);

    @Override
    public ApplicationContext getAppCtx() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
            throw new NullPointerException("facesContext");

        WebApplicationContext appContext = FacesContextUtils.getWebApplicationContext(facesContext);
        if (appContext == null)
            throw new NullPointerException("No application context in the Faces context");

        return appContext;
    }

    public HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object request = externalContext.getRequest();
        return (HttpServletRequest) request;
    }

    public UIComponent findComponent(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UIComponent component = viewRoot.findComponent(expr);

        if (component == null)
            throw new IllegalArgumentException("Illegal component id expr: " + expr);

        return component;
    }

    public ComponentHelper findComponentEx(String expr) {
        UIComponent component = findComponent(expr);
        if (component == null)
            return null;

        return new ComponentHelper(component);
    }

    public static final FacesPartialContext INSTANCE = new FacesPartialContext();

}
