package com.bee32.plover.inject.scope;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.spring.ScopeProxy;

@Component
@PerState
@ScopeProxy(ScopedProxyMode.TARGET_CLASS)
public class StateHint
        implements IStateHint {

    static final StateManager stateManager = StateManager.getGlobalStateManager();

    final StateContext context;
    String hint;

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

}
