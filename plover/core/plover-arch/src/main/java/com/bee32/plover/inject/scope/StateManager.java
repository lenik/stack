package com.bee32.plover.inject.scope;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

    Map<Object, StateContext> contextMap;

    Object state;
    StateContext context;

    public StateManager() {
        this("");
    }

    public StateManager(Object initialState) {
        contextMap = new HashMap<Object, StateContext>();
        setCurrentState(initialState);
    }

    public Object getCurrentState() {
        return state;
    }

    public void setCurrentState(Object state) {
        this.state = state;
        context = contextMap.get(state);
        if (context == null) {
            context = new StateContext();
            contextMap.put(state, context);
        }
    }

    public StateContext getCurrentContext() {
        return context;
    }

    static final StateManager global = new StateManager();

    public static StateManager getGlobalStateManager() {
        return global;
    }

}
