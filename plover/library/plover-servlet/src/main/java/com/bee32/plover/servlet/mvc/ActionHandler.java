package com.bee32.plover.servlet.mvc;

public abstract class ActionHandler
        implements IActionHandler {

    // protected static final String ACTION_NAME = "action.name";
    // protected static final String ACTION_PARAM = "action.param";

    protected static final int PRIORITY_HIGH = 0;
    protected static final int PRIORITY_DEFAULT = 10;
    protected static final int PRIORITY_LOW = 20;

    protected final String prefix;

    private ActionHandler(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        this.prefix = prefix;
    }

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    @Override
    public String getName() {
        String className = getClass().getSimpleName();
        String simpleName;
        if (className.endsWith("Handler"))
            simpleName = className.substring(0, className.length() - 7);
        else
            simpleName = className;

        return simpleName;
    }

}
