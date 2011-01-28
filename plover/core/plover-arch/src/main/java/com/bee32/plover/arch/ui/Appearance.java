package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.free.AbstractQueryable;
import javax.free.DisplayNameUtil;
import javax.free.DocUtil;
import javax.free.TagsUtil;

import com.bee32.plover.arch.util.LoadFlags32;

public class Appearance
        extends AbstractQueryable
        implements IAppearance, IImageMap, IRefdocs {

    private final Class<?> declaringType;

    private static final int HAVE_DISPLAY_NAME = 1 << 0;
    private static final int HAVE_DESCRIPTION = 1 << 1;
    private static final int HAVE_TAGS = 1 << 2;
    private LoadFlags32 flags;

    private String displayName;

    private String description;

    private Set<String> tags;

    public Appearance(Class<?> declaringType) {
        if (declaringType == null)
            throw new NullPointerException("declaringType");
        this.declaringType = declaringType;
    }

    @Override
    public String getDisplayName() {
        if (flags.checkAndLoad(HAVE_DISPLAY_NAME))
            displayName = DisplayNameUtil.getDisplayName(declaringType);
        return displayName;
    }

    @Override
    public String getDescription() {
        if (flags.checkAndLoad(HAVE_DESCRIPTION))
            description = DocUtil.getDoc(declaringType);
        return description;
    }

    @Override
    public IImageMap getImageMap() {
        return this;
    }

    @Override
    public IRefdocs getRefdocs() {
        return this;
    }

    @Override
    public Set<String> getTags() {
        if (flags.checkAndLoad(HAVE_TAGS)) {
            String[] tagArray = TagsUtil.getTags(declaringType);
            tags = new TreeSet<String>();
            for (String tag : tagArray)
                tags.add(tag);
        }
        return tags;
    }

    @Override
    public Iterable<? extends IRefdocEntry> getEntries(String tag) {
        return null;
    }

    @Override
    public IRefdocEntry getDefaultEntry(String tag) {
        return null;
    }

    @Override
    public URL getImage() {
        return null;
    }

    @Override
    public URL getImage(String variant) {
        return null;
    }

    @Override
    public URL getImage(String variant, int widthHint, int heightHint) {
        return null;
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
