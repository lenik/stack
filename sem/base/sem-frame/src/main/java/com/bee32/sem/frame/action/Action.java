package com.bee32.sem.frame.action;

import org.apache.commons.collections15.Closure;
import org.zkoss.zk.ui.event.Event;

import com.bee32.plover.arch.Component;
import com.bee32.plover.rtx.location.ILocationContext;

public class Action
        extends Component
        implements IAction {

    private boolean enabled = true;
    private ILocationContext target;
    private Closure<Event> zkCallback;

    public Action(ILocationContext actionTarget) {
        super();
        this.target = actionTarget;
    }

    public Action(String name, ILocationContext target) {
        super(name);
        this.target = target;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ILocationContext getTargetLocation() {
        return target;
    }

    public void setTarget(ILocationContext target) {
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
