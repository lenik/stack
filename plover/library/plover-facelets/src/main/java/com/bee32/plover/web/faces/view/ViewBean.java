package com.bee32.plover.web.faces.view;

import java.io.Serializable;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;
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

}
