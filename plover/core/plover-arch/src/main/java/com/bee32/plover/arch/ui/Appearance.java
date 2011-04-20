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
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.Flags32;

public abstract class Appearance
        extends AbstractQueryable
        implements IAppearance, IImageMap, IRefdocs {

    private IAppearance parent;

    private static final int HAVE_DISPLAY_NAME = 1 << 0;
    private static final int HAVE_DESCRIPTION = 1 << 1;
    private static final int HAVE_REFDOCS = 1 << 2;
    private static final int HAVE_IMAGE_MAP = 1 << 3;
    private Flags32 flags = new Flags32();

    private String displayName;
    private String description;
    private IRefdocs refdocs;
    private IImageMap imageMap;

    public Appearance(IAppearance parent) {
        this.parent = parent;
    }

    /**
     * Force to reload all fields.
     */
    protected void invalidate() {
        flags.checkAndClear(-1);
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

    @Override
    public String toString() {
        return getDisplayName();
    }

    /**
     * Find resource appearance first, if no available resource, fallback to annotation.
     * <p>
     * IA means Inject-first, Annotation-second.
     *
     * @param declaringClass
     *            The class whose annotations and resource bundle will be processed.
     * @param resourceResolver
     *            The resource resolver used to resolve the resource URLs declared in
     *            refdoc/imagemaps.
     * @return Non-<code>null</code> {@link InjectedAppearance}.
     */
    public static InjectedAppearance prepareAppearance(Class<?> declaringClass) {
        if (declaringClass == null)
            throw new NullPointerException("declaringClass");

        URL contextURL = ClassUtil.getContextURL(declaringClass);

        AnnotatedAppearance fallback = new AnnotatedAppearance(null, declaringClass, contextURL);

        InjectedAppearance injected = new InjectedAppearance(fallback, contextURL);

        return injected;
    }

    static final ClassLocal<InjectedAppearance> classLocalAppearance = new ClassLocal<InjectedAppearance>();

    public static InjectedAppearance prepareAppearanceCached(Class<?> declaringType) {
        InjectedAppearance appearance_IA = classLocalAppearance.get(declaringType);
        if (appearance_IA == null) {
            synchronized (Appearance.class) {
                appearance_IA = classLocalAppearance.get(declaringType);
                if (appearance_IA == null) {

                    appearance_IA = prepareAppearance(declaringType);

                    classLocalAppearance.put(declaringType, appearance_IA);
                }
            }
        }
        return appearance_IA;
    }

}
