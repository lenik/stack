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

}
