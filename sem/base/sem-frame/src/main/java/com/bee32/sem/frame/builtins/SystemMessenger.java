package com.bee32.sem.frame.builtins;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.test.AbstractSbml;

public class SystemMessenger
        extends AbstractSbml {

    static final Logger logger = LoggerFactory.getLogger(SystemMessenger.class);

    public static final String CHANNEL = "/system";

    @Override
    public void onSystemBcastMessage(String message) {
        sendMessage(message);
    }

    public static void sendMessage(String message) {
        logger.info("PrimePush " + CHANNEL + ": " + message);
        PushContext pushContext = PushContextFactory.getDefault().getPushContext();
        pushContext.push(CHANNEL, "color: red;; 系统消息：" + message);
    }

}
