package com.bee32.plover.inject.scope;

import java.util.Map;

public class StateScope
        extends AbstractScope {

    StateManager stateManager = StateManager.getGlobalStateManager();

    @Override
    protected Map<String, Object> getBeanMap() {
        StateContext context = stateManager.getCurrentContext();
        Map<String, Object> attributes = context.getAttributes();
        return attributes;
    }

    @Override
    public String getConversationId() {
        Object state = stateManager.getCurrentState();
        int stateId = System.identityHashCode(state);
        String convId = String.valueOf(stateId);
        return convId;
    }

}
