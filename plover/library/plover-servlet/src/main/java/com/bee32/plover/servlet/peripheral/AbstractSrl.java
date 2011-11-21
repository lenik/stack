package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletRequestEvent;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractSrl
        implements IServletRequestListener {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

}
