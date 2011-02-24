package com.bee32.icsf.access;

import java.io.Serializable;

import com.bee32.icsf.access.authority.IAuthority;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.ui.res.InjectedAppearance;

/**
 * <em><font color='red'>The modal logic of belief should be considered in more detail. </font></em>
 */
public abstract class Permission
        implements IAppearance, Serializable {

    private static final long serialVersionUID = 1L;

    protected final InjectedAppearance sink;

    public Permission() {
        sink = new InjectedAppearance(getClass());
    }

    public abstract String getName();

    @Override
    public String getDisplayName() {
        String displayName = sink.getDisplayName();
        // if (displayName == null)
        // displayName = getName();
        return displayName;
    }

    @Override
    public String getDescription() {
        return sink.getDescription();
    }

    @Override
    public IImageMap getImageMap() {
        return sink.getImageMap();
    }

    @Override
    public IRefdocs getRefdocs() {
        return sink.getRefdocs();
    }

    public abstract IAuthority getAuthority();

    public abstract boolean implies(Permission permission);

    public Permission reduce() {
        return this;
    }

}
