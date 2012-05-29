package com.bee32.sem.frame.menu;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;

public abstract class AbstractMenuBuilder<T>
        implements IMenuBuilder<T>, IMenuAssembler {

    boolean showAll = false;
    final HttpServletRequest request;
    IMenuAssembler assembler;

    protected AbstractMenuBuilder(HttpServletRequest request) {
        this.request = request;
        assembler = new DefaultMenuAssembler();
    }

    @Override
    public <M extends MenuComposite> M require(Class<M> mcClass) {
        return assembler.require(mcClass);
    }

    @Override
    public boolean isShowAll() {
        return showAll;
    }

    @Override
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    public final T buildMenubar(IMenuNode virtualRoot) {
        ContextMenuAssembler.setMenuAssembler(this);

        for (Class<? extends MenuComposite> mcClass : MenuCompositeManager.load()) {
            // profile parameters here...?
            require(mcClass);
        }

        T target = buildMenubarImpl(virtualRoot);

        ContextMenuAssembler.removeMenuAssembler();
        return target;
    }

    protected abstract T buildMenubarImpl(IMenuNode virtualRoot);

    protected String resolve(ILocationContext location) {
        HttpServletRequest request = this.request;
        if (request == null)
            request = ThreadServletContext.getRequestOpt();

        if (request == null)
            return location.toString();

        String cr = location.resolveAbsolute(request);
        return cr;
    }

}
