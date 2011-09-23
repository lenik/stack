package com.bee32.plover.inject.scope;

import org.springframework.beans.factory.config.Scope;

public abstract class AbstractScope
        implements Scope {

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // Not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

}
