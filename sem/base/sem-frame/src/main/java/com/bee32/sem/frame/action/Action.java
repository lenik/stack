package com.bee32.sem.frame.action;

import javax.faces.event.ActionListener;

import com.bee32.plover.arch.Component;
import com.bee32.plover.rtx.location.ILocationContext;

public class Action
        extends Component
        implements IAction {

    private boolean enabled = true;
    private ILocationContext target;
    private ActionListener actionListener;

    public Action() {
    }

    public Action(ILocationContext actionTarget) {
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
    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public String toString() {
        String targetStr = String.valueOf(target);
        if (!enabled)
            targetStr += " (disabled)";
        return targetStr;
    }

}
