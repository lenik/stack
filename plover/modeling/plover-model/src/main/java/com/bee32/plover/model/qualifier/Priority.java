package com.bee32.plover.model.qualifier;

public class Priority
        extends Qualifier<Priority> {

    private static final long serialVersionUID = 1L;

    private int qualifierPriority;
    private int priority;

    public Priority(String name, int qualifierPriority, int priority) {
        super(Priority.class, name);
        this.qualifierPriority = qualifierPriority;
        this.priority = priority;
    }

    public Priority(String name, int priority) {
        this(name, StdPriority.priority, priority);
    }

    @Override
    public int getQualifierPriority() {
        return qualifierPriority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    protected int hashCodeSpecific() {
        int hash = qualifierPriority * 0x31;
        hash = (hash << 4) ^ priority * 0x31;
        return hash;
    }

    @Override
    public boolean equalsSpecific(Priority o) {
        if (qualifierPriority != o.qualifierPriority)
            return false;
        if (priority != o.priority)
            return false;
        return true;
    }

}
