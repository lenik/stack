package com.bee32.sem.frame.action;

import com.bee32.plover.arch.Component;
import com.bee32.plover.servlet.context.ContextLocation;

public class Action
        extends Component
        implements IAction {

    private boolean enabled = true;
    private ContextLocation actionTarget;

    private Action(String name, ContextLocation actionTarget) {
        super(name);
        this.actionTarget = actionTarget;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ContextLocation getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(ContextLocation actionTarget) {
        this.actionTarget = actionTarget;
    }

}
