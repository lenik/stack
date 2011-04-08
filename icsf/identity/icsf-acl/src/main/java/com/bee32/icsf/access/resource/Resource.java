package com.bee32.icsf.access.resource;

import com.bee32.plover.arch.Component;

/**
 * Base class for ICSF-Protected Resource
 */
public abstract class Resource
        extends Component {

    private static final long serialVersionUID = 1L;

    public Resource() {
        super();
    }

    public Resource(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Like imples().
     */
    protected boolean contains(Resource resource) {
        return this == resource;
    }

}
