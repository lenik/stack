package com.bee32.plover.arch;

import java.util.Collection;
import java.util.Map;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.TreeMapNode;
import com.bee32.plover.arch.operation.IOperation;

public abstract class Module
        extends Component
        implements IModule {

    private TreeMapNode<Object> nodeImpl = new TreeMapNode<Object>(Object.class);

    private Credit credit = Credit.dummy;

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
        nodeImpl.addChild(token, obj);
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
    public int getPriority() {
        return nodeImpl.getPriority();
    }

    @Override
    public INamedNode getParent() {
        return nodeImpl.getParent();
    }

    @Override
    public Class<?> getChildType() {
        return nodeImpl.getChildType();
    }

    @Override
    public boolean hasChild(Object obj) {
        return nodeImpl.hasChild(obj);
    }

    @Override
    public Object getChild(String location) {
        return nodeImpl.getChild(location);
    }

    @Override
    public String getChildName(Object obj) {
        return nodeImpl.getChildName(obj);
    }

    @Override
    public Collection<String> getChildNames() {
        return nodeImpl.getChildNames();
    }

    @Override
    public IOperation getOperation(String name) {
        return nodeImpl.getOperation(name);
    }

    @Override
    public Collection<IOperation> getOperations() {
        return nodeImpl.getOperations();
    }

    @Override
    public Map<String, IOperation> getOperationMap() {
        return nodeImpl.getOperationMap();
    }

}
