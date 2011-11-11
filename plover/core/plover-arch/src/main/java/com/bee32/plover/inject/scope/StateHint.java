package com.bee32.plover.inject.scope;

import javax.inject.Inject;

public class StateHint
        implements IStateHint {

    static final StateManager stateManager = StateManager.getGlobalStateManager();

    final StateContext context;
    String hint;

// @Autowired
    @Inject
    OtherState other;

    public StateHint() {
        context = stateManager.getCurrentContext();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public StateContext getContext() {
        return context;
    }

    @Override
    public String getHint() {
        return hint;
    }

    @Override
    public void setHint(String hint) {
        this.hint = hint;
        if (other != null)
            other.setOther(hint + "-other");
    }

    @Override
    public String toString() {
        String s = hint;
        if (other != null)
            s += " : " + other.getOther();
        return s;
    }

}
