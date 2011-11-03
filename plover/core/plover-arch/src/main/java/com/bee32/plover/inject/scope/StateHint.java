package com.bee32.plover.inject.scope;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
@PerState
public class StateHint
        implements IStateHint {

    static final StateManager stateManager = StateManager.getGlobalStateManager();

    final StateContext context;
    String hint;

    @Inject
    IOtherState other;

    public StateHint() {
        context = stateManager.getCurrentContext();
    }

    public StateManager getStatemanager() {
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
    }

    @Override
    public String toString() {
        return hint;
    }

    public IOtherState getOther() {
        return other;
    }

    public void setOther(IOtherState other) {
        this.other = other;
    }

}
