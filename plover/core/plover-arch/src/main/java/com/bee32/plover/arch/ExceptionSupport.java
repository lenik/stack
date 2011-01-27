package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.List;

import javax.free.RecoverableExceptionEvent;
import javax.free.RecoverableExceptionListener;
import javax.free.RecoverableExceptionSource;

public class ExceptionSupport
        implements RecoverableExceptionSource {

    private final List<RecoverableExceptionListener> listeners;

    public ExceptionSupport() {
        this.listeners = new ArrayList<RecoverableExceptionListener>(1);
    }

    @Override
    public void addExceptionListener(RecoverableExceptionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeExceptionListener(RecoverableExceptionListener listener) {
        listeners.remove(listener);
    }

    public void throwException(Exception e) {
        for (RecoverableExceptionListener listener : listeners)
            listener.exceptionThrown(e);
    }

    public boolean recoverException(Object source, Exception e) {
        RecoverableExceptionEvent event = new RecoverableExceptionEvent(source, e);
        for (RecoverableExceptionListener listener : listeners) {
            listener.recoverException(event);
            if (event.isRecovered())
                return true;
        }
        return false;
    }

}
