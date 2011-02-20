package com.bee32.plover.arch;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.locator.IObjectLocator;
import com.bee32.plover.arch.locator.MapLocator;

public abstract class Module
        extends Component
        implements IModule {

    private MapLocator<Object> locatorImpl = new MapLocator<Object>(Object.class);

    private Credit credit;

    public Module() {
        super();
    }

    public Module(String name) {
        super(name);
    }

    /**
     * Declare the ownership for specific objects of a single type.
     *
     * @param token
     *            Non-<code>null</code> token component appeared in the RESTful resource URI.
     *            <p>
     *            If the <code>token</code> is already existed, it will be replaced by the new
     *            <code>locator</code>.
     * @param locator
     *            Non-<code>null</code> object locator.
     */
    protected void declare(String token, Object obj) {
        locatorImpl.setLocation(token, obj);
    }

    @Override
    public Credit getCredit() {
        return credit;
    }

    /**
     * Make it final if you don't want it be modified by any future derivations.
     */
    protected void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public String getCopyright() {
        return null;
    }

    // IObjectLocator

    @Override
    public IObjectLocator getParent() {
        return locatorImpl.getParent();
    }

    @Override
    public Class<?> getBaseType() {
        return locatorImpl.getBaseType();
    }

    @Override
    public int getPriority() {
        return locatorImpl.getPriority();
    }

    @Override
    public Object locate(String location) {
        return locatorImpl.locate(location);
    }

    @Override
    public boolean isLocatable(Object obj) {
        return locatorImpl.isLocatable(obj);
    }

    @Override
    public String getLocation(Object obj) {
        return locatorImpl.getLocation(obj);
    }

}
