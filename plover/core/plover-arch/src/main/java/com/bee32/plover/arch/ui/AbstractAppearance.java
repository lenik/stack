package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractQueryable;

public abstract class AbstractAppearance
        extends AbstractQueryable
        implements IAppearance, IImageMap, IRefdocs {

    IAppearance parent;

    public AbstractAppearance(IAppearance parent) {
        this.parent = parent;
    }

    // IRefdocs delegates

    @Override
    public Set<String> getTags() {
        IRefdocs refdocs = getRefdocs();
        if (refdocs == null)
            return null;
        return refdocs.getTags();
    }

    @Override
    public Collection<? extends IRefdocEntry> getEntries(String tag) {
        IRefdocs refdocs = getRefdocs();
        if (refdocs == null)
            return null;
        return refdocs.getEntries(tag);
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        IRefdocs refdocs = getRefdocs();
        if (refdocs == null)
            if (parent != null)
                return parent.getRefdocs().getDefaultEntry(tag);
            else
                return null;
        return refdocs.getDefaultEntry(tag);
    }

    // IImageMap delegates

    @Override
    public URL getImage() {
        IImageMap imageMap = getImageMap();
        if (imageMap == null)
            return null;
        return imageMap.getImage();
    }

    @Override
    public URL getImage(String qualifier) {
        IImageMap imageMap = getImageMap();
        if (imageMap == null)
            return null;
        return imageMap.getImage(qualifier);
    }

    @Override
    public URL getImage(String qualifier, int widthHint, int heightHint) {
        IImageMap imageMap = getImageMap();
        if (imageMap == null)
            return null;
        return imageMap.getImage(qualifier, widthHint, heightHint);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    static final Map<Class<?>, Integer> traitsMap;
    static {
        traitsMap = new HashMap<Class<?>, Integer>();
        traitsMap.put(IImageMap.class, IImageMap.traitsIndex);
        traitsMap.put(IRefdocs.class, IRefdocs.traitsIndex);
        traitsMap.put(IAppearance.class, TraitsIndex.APPEARANCE);
    }

    @Override
    public <X> X query(Class<X> specificationType) {
        Integer localIndex = traitsMap.get(specificationType);
        if (localIndex == null)
            return super.query(specificationType);
        Object traits = query(localIndex.intValue());
        return specificationType.cast(traits);
    }

    // @Override
    protected Object query(int traitsIndex) {
        switch (traitsIndex) {
        case IImageMap.traitsIndex:
            return this;
        case IRefdocs.traitsIndex:
            return this;
        case TraitsIndex.APPEARANCE:
            return this;
        }
        return null;
    }

}
