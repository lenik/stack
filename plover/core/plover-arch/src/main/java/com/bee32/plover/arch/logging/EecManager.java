package com.bee32.plover.arch.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class EecManager {

    List<IExceptionEntryCompletion> completions = new ArrayList<>();

    public EecManager() {
        for (IExceptionEntryCompletion targeter : ServiceLoader.load(IExceptionEntryCompletion.class))
            completions.add(targeter);
    }

    public void completeEntry(ExceptionLogEntry entry) {
        for (IExceptionEntryCompletion completion : completions)
            completion.completeEntry(entry);
    }

    static EecManager instance;

    public static EecManager getInstance() {
        if (instance == null) {
            synchronized (EecManager.class) {
                if (instance == null) {
                    instance = new EecManager();
                }
            }
        }
        return instance;
    }

}
