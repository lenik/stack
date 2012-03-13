package com.bee32.plover.servlet.test;

import java.util.ServiceLoader;

public class QuitListeners {

    Iterable<IQuitListener> listeners;

    public QuitListeners() {
        listeners = ServiceLoader.load(IQuitListener.class);
    }

    public void quit() {
        for (IQuitListener listener : listeners)
            listener.quit();
    }

    static QuitListeners instance;

    public static synchronized QuitListeners getInstance() {
        if (instance == null)
            instance = new QuitListeners();
        return instance;
    }

}
