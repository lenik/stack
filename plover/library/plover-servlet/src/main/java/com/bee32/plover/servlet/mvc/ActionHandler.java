package com.bee32.plover.servlet.mvc;

public abstract class ActionHandler
        implements IActionHandler {

    // protected static final String ACTION_NAME = "action.name";
    // protected static final String ACTION_PARAM = "action.param";

    protected static final int PRIORITY_HIGH = 0;
    protected static final int PRIORITY_DEFAULT = 10;
    protected static final int PRIORITY_LOW = 20;

    final String actionName;

    protected ActionHandler() {
        this(null);
    }

    protected ActionHandler(String actionName) {
        if (actionName == null) {
            String className = getClass().getSimpleName();
            if (className.endsWith("Handler"))
                actionName = className.substring(0, className.length() - 7);
            else
                actionName = className;
        }
        this.actionName = actionName;
    }

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    @Override
    public String getName() {
        return actionName;
    }

}
