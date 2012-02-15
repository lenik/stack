package com.bee32.plover.arch.logging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.log4j.spi.ThrowableInformation;

public class EltManager {

    List<IExceptionLogTargeter> logTargets = new ArrayList<>();

    public EltManager() {
        for (IExceptionLogTargeter targeter : ServiceLoader.load(IExceptionLogTargeter.class))
            logTargets.add(targeter);
    }

    public void addException(Object message, ThrowableInformation ti) {
        for (IExceptionLogTargeter logTarget : logTargets) {
            Collection<? extends ExceptionLog> logGroup = logTarget.getLogTargets();
            for (ExceptionLog log : logGroup) {
                log.addException(message, ti);
            }
        }
    }

    static EltManager instance;

    public static EltManager getInstance() {
        if (instance == null) {
            synchronized (EltManager.class) {
                if (instance == null) {
                    instance = new EltManager();
                }
            }
        }
        return instance;
    }

}
