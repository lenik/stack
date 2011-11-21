package com.bee32.plover.servlet.peripheral;

import javax.servlet.http.HttpSessionEvent;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractHsl
        implements IHttpSessionListener {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

}
