package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractQueryable;
import javax.free.ClassLocal;

import com.bee32.plover.arch.ui.annotated.AnnotatedAppearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.LoadFlags32;
import com.bee32.plover.arch.util.res.ClassResourceResolver;
import com.bee32.plover.arch.util.res.IResourceResolver;
import com.bee32.plover.arch.util.res.StemDispatchStrategy;

public abstract class Appearance
        extends AbstractQueryable
        implements IAppearance, IImageMap, IRefdocs {

    private final Object target;

    private IAppearance parent;

    private static final int HAVE_DISPLAY_NAME = 1 << 0;
    private static final int HAVE_DESCRIPTION = 1 << 1;
    private static final int HAVE_REFDOCS = 1 << 2;
    private static final int HAVE_IMAGE_MAP = 1 << 3;
    private LoadFlags32 flags = new LoadFlags32();

    private String displayName;
    private String description;
    private IRefdocs refdocs;
    private IImageMap imageMap;

    public Appearance(Object target, IAppearance parent) {
        this.target = target;
        this.parent = parent;
    }

    /**
     * Find and load display name.
     *
     * @return <code>null</code> if not available.
     */
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

    /**
     * Find and load description.
     *
     * @return <code>null</code> if not available.
     */

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

    /**
     * Find and load refdocs.
     *
     * @return <code>null</code> if not available.
     */
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

    /**
     * Find and load image-map.
     *
     * @return <code>null</code> if not available.
     */

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

    public Object getTarget() {
        return target;
    }

    @Override
    public String toString() {
        String appearanceType = getClass().getSimpleName();
        return appearanceType + "<" + target + ">";
    }

    /**
     * Find resource appearance first, if no available resource, fallback to annotation.
     */
    public static InjectedAppearance getAppearance(Object target, IResourceResolver resourceResolver,
            Class<?> declaringType) {
        Appearance fallback = new AnnotatedAppearance(null, declaringType);

        InjectedAppearance injected = new InjectedAppearance(target, fallback, resourceResolver);

        new StemDispatchStrategy(injected).bind(declaringType);

        return injected;
    }

    /**
     * Find annotated appearance first, if no available annotation, fallback to resource.
     */
    public static Appearance getAppearance(Object target, Class<?> declaringType, IResourceResolver resourceResolver) {
        Appearance fallback = new InjectedAppearance(target, null, resourceResolver);
        Appearance appearance = new AnnotatedAppearance(fallback, declaringType);
        return appearance;
    }

    static final ClassLocal<InjectedAppearance> classLocalAppearance = new ClassLocal<InjectedAppearance>();

    public static InjectedAppearance getAppearance(Class<?> declaringType) {
        InjectedAppearance appearance = classLocalAppearance.get(declaringType);
        if (appearance == null) {
            synchronized (Appearance.class) {
                appearance = classLocalAppearance.get(declaringType);
                if (appearance == null) {

                    IResourceResolver resourceResolver = new ClassResourceResolver(declaringType);

                    appearance = getAppearance(null, resourceResolver, declaringType);

                    new StemDispatchStrategy(appearance).bind(declaringType);

                    classLocalAppearance.put(declaringType, appearance);
                }
            }
        }
        return appearance;
    }
}
