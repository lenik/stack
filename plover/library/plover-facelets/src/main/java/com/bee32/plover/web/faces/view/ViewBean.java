package com.bee32.plover.web.faces.view;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.web.faces.utils.ComponentHelper;
import com.bee32.plover.web.faces.utils.FacesContextSupport;

@ComponentTemplate
@Lazy
@Scope("view")
public abstract class ViewBean
        extends FacesContextSupport
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public ViewBean() {
        wire();
    }

    /**
     * TODO Is this safe for view-bean?
     */
    protected void wire() {
        ApplicationContext context = ThreadHttpContext.requireApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }

    protected UIComponent findComponent(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        UIComponent component = viewRoot.findComponent(expr);

        if (component == null)
            throw new IllegalArgumentException("Illegal component id expr: " + expr);

        return component;
    }

    protected ComponentHelper findComponentEx(String expr) {
        UIComponent component = findComponent(expr);
        if (component == null)
            return null;

        return new ComponentHelper(component);
    }

}
