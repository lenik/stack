package com.bee32.icsf.access.shield;

import java.io.Closeable;

public class AclFrameHandler
        implements Closeable {

    AclOptionStack stack;

    public AclFrameHandler(AclOptionStack stack) {
        if (stack == null)
            throw new NullPointerException("stack");
        this.stack = stack;
    }

    @Override
    public synchronized void close() {
        if (stack != null) {
            stack.leave();
            stack = null;
        }
    }

}
