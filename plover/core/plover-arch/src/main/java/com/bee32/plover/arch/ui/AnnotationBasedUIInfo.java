package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.free.AbstractCommonTraits;
import javax.free.DisplayNameUtil;
import javax.free.DocUtil;
import javax.free.TagsUtil;

public abstract class AnnotationBasedUIInfo<T>
        extends AbstractCommonTraits<T>
        implements IUIInfo, IImageMap, IRefdocs {

    public AnnotationBasedUIInfo(Class<T> type) {
        super(type);
    }

    @Override
    public String getDisplayName() {
        return DisplayNameUtil.getDisplayName(getClass());
    }

    @Override
    public String getDescription() {
        return DocUtil.getDoc(getClass());
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
        String[] tags = TagsUtil.getTags(getClass());
        Set<String> tagsSet = new TreeSet<String>();
        for (String tag : tags)
            tagsSet.add(tag);
        return tagsSet;
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
        traitsMap.put(IUIInfo.class, IUIInfo.traitsIndex);
    }

    @Override
    public <X> X query(Class<X> specificationType) {
        Integer localIndex = traitsMap.get(specificationType);
        if (localIndex == null)
            return super.query(specificationType);
        Object traits = query(localIndex);
        return specificationType.cast(traits);
    }

    @Override
    protected Object query(int traitsIndex) {
        switch (traitsIndex) {
        case IImageMap.traitsIndex:
            return this;
        case IRefdocs.traitsIndex:
            return this;
        case IUIInfo.traitsIndex:
            return this;
        }
        return null;
    }

}
