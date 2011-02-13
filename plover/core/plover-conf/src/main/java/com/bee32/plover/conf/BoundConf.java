package com.bee32.plover.conf;

import java.util.Collection;

import com.bee32.plover.model.Model;

public abstract class BoundConf
        extends Model
        implements IConf {

    private static final long serialVersionUID = 1L;

    public BoundConf() {
        super();
    }

    public BoundConf(String name) {
        super(name);
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
