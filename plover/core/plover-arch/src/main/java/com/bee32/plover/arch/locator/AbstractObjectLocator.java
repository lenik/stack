package com.bee32.plover.arch.locator;

public abstract class AbstractObjectLocator
        implements IObjectLocator {

    private final IObjectLocator parent;
    private final Class<?> baseType;

    /**
     * <ul>
     * <li>base = null, parent != null
     * <li>base != null, parent = null
     * <li>base != null, parent != null: base is ignored.
     * </ul>
     */
    public AbstractObjectLocator(Class<?> baseType, IObjectLocator parent) {
        if (baseType == null && parent == null)
            throw new NullPointerException("Both baseType and parent are null");
        this.parent = parent;
        this.baseType = baseType;

        if (baseType != null) {
            ObjectLocatorRegistry registry = ObjectLocatorRegistry.getInstance();
            registry.register(this);
        }
    }

    @Override
    public IObjectLocator getParent() {
        return parent;
    }

    @Override
    public Class<?> getBaseType() {
        return baseType;
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
