package com.bee32.plover.servlet.test;

import java.util.ServiceLoader;

public class SystemBcastMessageListeners {

    public static void bcastSystemMessage(String message) {
        for (ISystemBcastMessageListener sbml : ServiceLoader.load(ISystemBcastMessageListener.class)) {
            sbml.onSystemBcastMessage(message);
        }
    }

}
