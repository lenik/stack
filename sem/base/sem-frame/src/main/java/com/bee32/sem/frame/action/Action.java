package com.bee32.sem.frame.action;

import org.apache.commons.collections15.Closure;
import org.zkoss.zk.ui.event.Event;

import com.bee32.plover.arch.Component;
import com.bee32.plover.servlet.context.ContextLocation;

public class Action
        extends Component
        implements IAction {

    private boolean enabled = true;
    private ContextLocation target;
    private Closure<Event> zkCallback;

    public Action(ContextLocation actionTarget) {
        super();
        this.target = actionTarget;
    }

    public Action(String name, ContextLocation target) {
        super(name);
        this.target = target;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ContextLocation getTargetLocation() {
        return target;
    }

    public void setTarget(ContextLocation target) {
        this.target = target;
    }

    @Override
    public Closure<Event> getZkCallback() {
        return zkCallback;
    }

    public void setZkCallback(Closure<Event> zkCallback) {
        this.zkCallback = zkCallback;
    }

    @Override
    public String toString() {
        String targetStr = String.valueOf(target);
        if (!enabled)
            targetStr += " (disabled)";
        return targetStr;
    }

}
