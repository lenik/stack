package com.bee32.plover.web.faces.view;

import java.io.Serializable;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@ComponentTemplate
@Scope("view")
public class ViewBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public ViewBean() {
        wire();
    }

    protected void wire() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }

}
