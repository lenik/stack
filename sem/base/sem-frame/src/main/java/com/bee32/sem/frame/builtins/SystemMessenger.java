package com.bee32.sem.frame.builtins;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

public class SystemMessenger {

    public static final String CHANNEL = "/system";

    public static void sendMessage(String message) {
        PushContext pushContext = PushContextFactory.getDefault().getPushContext();
        pushContext.push(CHANNEL, message);
    }

}
