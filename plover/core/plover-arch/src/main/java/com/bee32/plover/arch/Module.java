package com.bee32.plover.arch;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.naming.INamed;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.TreeMapNode;
import com.bee32.plover.arch.service.IServiceContribution;
import com.bee32.plover.arch.service.PloverService;
import com.bee32.plover.inject.ServiceTemplate;

/**
 * &#64;Configuration is not inheritable, so you should add Configuration annotation in all concrete
 * Module classes, to enable Spring usage.
 * <p>
 * <strike> For Spring usage: Module must not be in lazy-init mode, otherwise, ModuleLoader won't
 * know it. </strike>
 *
 * Lazy component is initialized when getBeanOfType() is invoked.
 */
@ServiceTemplate
public abstract class Module
        extends Component
        implements IModule, INamed {

    private TreeMapNode<Object> nodeImpl = new TreeMapNode<Object>(Object.class);
    private Credit credit = Credit.dummy;

    private boolean loaded;
    private ApplicationContext appctx;

    public Module() {
        super();
        // preamble();
    }

    public Module(String name) {
        super(name);
        // preamble();
    }

    @Override
    public synchronized void load() {
        if (!loaded) {
            preamble();
            loaded = true;
        }
    }

    @Override
    public void unload() {
    }

    /**
     * In the preamble, you should:
     * <ol>
     * <li>Configure the module.
     * <li>Add service contributions by {@link #contribute(IServiceContribution) contribute} method.
     * <li>Export resources, entities for public access.
     * <ol>
     */
    protected abstract void preamble();

    /**
     * Invoked after appctx is initialized.
     */
    protected void assemble() {
    }

    protected final <T> T getBean(Class<T> beanType) {
        return getAppCtx().getBean(beanType);
    }

    protected final ApplicationContext getAppCtx() {
        if (appctx == null)
            throw new IllegalStateException("appctx is only available in preamble2");
        return appctx;
    }

    protected final void contribute(IServiceContribution<?> contribution) {
        PloverService.contribute(contribution);
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

        if (obj instanceof INamedNode) {
            INamedNode child = (INamedNode) obj;
            child.setParent(this);
        }
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

    // TreeNodeMap

    @Override
    public int getPriority() {
        return nodeImpl.getPriority();
    }

    @Override
    public String refName() {
        return getName();
    }

    @Override
    public INamedNode getParent() {
        return nodeImpl.getParent();
    }

    @Override
    public void setParent(INamedNode parent) {
        nodeImpl.setParent(parent);
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
    public Iterable<?> getChildren() {
        return nodeImpl.getChildren();
    }

}
