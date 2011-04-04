package com.bee32.plover.conf;

import java.util.Collection;

import javax.free.IContext;

import com.bee32.plover.arch.Component;

public class ContextConf
        extends Component
        implements IConf {

    private static final long serialVersionUID = 1L;

    protected final IContext context;

    public ContextConf(IContext context) {
        super();
        if (context == null)
            throw new NullPointerException("context");
        this.context = context;
    }

    @Override
    public Collection<String> getKeys() {
        return null;
    }

    @Override
    public Object get(String key) {
        return null;
    }

}
