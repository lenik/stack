package com.bee32.sem.frame.builtins;

import org.primefaces.context.RequestContext;

import com.bee32.plover.faces.view.ViewBean;

public class SystemMessenger
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public static final String CHANNEL = "system";

    public void sendMessage(String message) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        ctx.push(CHANNEL, message);
    }

    static SystemMessenger instance;

    public static SystemMessenger getInstance() {
        if (instance == null) {
            synchronized (SystemMessenger.class) {
                if (instance == null) {
                    instance = new SystemMessenger();
                }
            }
        }
        return instance;
    }

}
