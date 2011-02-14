package com.bee32.plover.conf;

import javax.free.AbstractContext;
import javax.free.IContext;

public abstract class Style
        extends AbstractContext {

    private IContext parentContext;

    public Style(String styleName, IContext parentContext) {
        super(styleName);
        this.parentContext = parentContext;
    }

    // public Style(String styleName, IContext... parentContexts) {
    // super(styleName);
    // this.parentContext = constructParents(parentContexts);
    // }

    @Override
    public IContext getParentContext() {
        return parentContext;
    }

    static IContext constructParents(IContext... parentContexts) {
        if (parentContexts == null || parentContexts.length == 0)
            throw new IllegalArgumentException("No parent");
        if (parentContexts.length > 1)
            throw new UnsupportedOperationException("Unsupport to merge parents");
        return parentContexts[0];
    }

    public abstract void setUp()
            throws StyleSetupException;

    public abstract void tearDown();

}
