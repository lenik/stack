package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractQueryable;

import com.bee32.plover.arch.util.LoadFlags32;

public abstract class Appearance
        extends AbstractQueryable
        implements IAppearance, IImageMap, IRefdocs {

    private IAppearance parent;

    private static final int HAVE_DISPLAY_NAME = 1 << 0;
    private static final int HAVE_DESCRIPTION = 1 << 1;
    private static final int HAVE_REFDOCS = 1 << 2;
    private static final int HAVE_IMAGE_MAP = 1 << 3;
    private LoadFlags32 flags;

    private String displayName;
    private String description;
    private IRefdocs refdocs;
    private IImageMap imageMap;

    public Appearance(IAppearance parent) {
        this.parent = parent;
    }

    protected abstract String loadDisplayName();

    @Override
    public String getDisplayName() {
        if (flags.checkAndLoad(HAVE_DISPLAY_NAME)) {
            displayName = loadDisplayName();
            if (displayName == null && parent != null)
                return parent.getDisplayName();
        }
        return displayName;
    }

    protected abstract String loadDescription();

    @Override
    public String getDescription() {
        if (flags.checkAndLoad(HAVE_DESCRIPTION)) {
            description = loadDescription();
            if (description == null && parent != null)
                description = parent.getDescription();
        }
        return description;
    }

    protected abstract IRefdocs loadRefdocs();

    @Override
    public final IRefdocs getRefdocs() {
        if (flags.checkAndLoad(HAVE_REFDOCS)) {
            refdocs = loadRefdocs();
            if (refdocs == null && parent != null)
                refdocs = parent.getRefdocs();
        }
        return refdocs;
    }

    protected abstract IImageMap loadImageMap();

    @Override
    public final IImageMap getImageMap() {
        if (flags.checkAndLoad(HAVE_IMAGE_MAP)) {
            imageMap = loadImageMap();
            if (imageMap == null && parent != null)
                imageMap = parent.getImageMap();
        }
        return imageMap;
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
            return parent.getRefdocs().getDefaultEntry(tag);
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
