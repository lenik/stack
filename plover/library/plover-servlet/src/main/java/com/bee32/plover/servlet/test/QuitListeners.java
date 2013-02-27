package com.bee32.plover.servlet.test;

import java.util.ServiceLoader;

public class QuitListeners {

    Iterable<IQuitListener> listeners;

    public QuitListeners() {
        listeners = ServiceLoader.load(IQuitListener.class);
    }

    public int quit() {
        int maxDelay = 0;
        for (IQuitListener listener : listeners) {
            int delay = listener.quit();
            if (delay > maxDelay)
                maxDelay = delay;
        }
        return maxDelay;
    }

    static QuitListeners instance;

    public static synchronized QuitListeners getInstance() {
        if (instance == null)
            instance = new QuitListeners();
        return instance;
    }

}
