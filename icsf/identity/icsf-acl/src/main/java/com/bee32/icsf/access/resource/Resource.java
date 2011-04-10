package com.bee32.icsf.access.resource;

import com.bee32.plover.arch.Component;

/**
 * Base class for ICSF-Protected Resource
 */
public abstract class Resource
        extends Component {

    private static final long serialVersionUID = 1L;

    public Resource(String name) {
        super(name);
    }

    /**
     * Get the resource local-name.
     *
     * @return Non-<code>null</code> resource name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set resource local-name
     *
     * @param name
     *            New local-name of the resource, must be non-<code>null</code>.
     */
    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    /**
     * Like imples().
     */
    protected boolean contains(Resource resource) {
        if (this == resource)
            return true;

        if (resource == null)
            throw new NullPointerException("resource");

        if (!getClass().equals(resource.getClass()))
            return false;

        String oName = resource.getName();
        if (oName.startsWith(this.name)) {
            int len = this.name.length();
            if (oName.length() == len)
                return true;
            if (oName.length() > len && oName.charAt(len) == '.')
                return true;
        }

        return false;
    }

}
