package com.bee32.plover.faces.diag;

import java.lang.Thread.State;

public class ThreadWrapper {

    final Thread thread;

    public ThreadWrapper(Thread thread) {
        if (thread == null)
            throw new NullPointerException("thread");
        this.thread = thread;
    }

    public boolean isInterrupted() {
        return thread.isInterrupted();
    }

    public final boolean isAlive() {
        return thread.isAlive();
    }

    public final int getPriority() {
        return thread.getPriority();
    }

    public final String getName() {
        return thread.getName();
    }

    public final ThreadGroup getThreadGroup() {
        return thread.getThreadGroup();
    }

    public final boolean isDaemon() {
        return thread.isDaemon();
    }

    public StackTraceElement[] getStackTrace() {
        return thread.getStackTrace();
    }

    public long getId() {
        return thread.getId();
    }

    public State getState() {
        return thread.getState();
    }

}
