package com.bee32.plover.arch.ui;

import java.net.URL;

import javax.free.ClassLocal;

import com.bee32.plover.arch.ui.annotated.AnnotatedAppearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.Flags32;

public abstract class LazyAppearance
        extends AbstractAppearance {

    private static final int HAVE_LABEL = 1 << 0;
    private static final int HAVE_DESCRIPTION = 1 << 1;
    private static final int HAVE_REFDOCS = 1 << 2;
    private static final int HAVE_IMAGE_MAP = 1 << 3;
    private Flags32 flags = new Flags32();

    private String label;
    private String description;
    private IRefdocs refdocs;
    private IImageMap imageMap;

    public LazyAppearance(IAppearance parent) {
        super(parent);
    }

    /**
     * Force to reload all fields.
     */
    protected void invalidate() {
        flags.checkAndClear(-1);
    }

    /**
     * Find and load l
     *
     * @return <code>null</code> if not available.
     */
    protected abstract String loadLabel();

    @Override
    public String getDisplayName() {
        if (flags.checkAndLoad(HAVE_LABEL)) {
            label = loadLabel();

            // Inherit appearance if local label isn't defined.
            if (label == null && parent != null)
                label = parent.getDisplayName();

            // Otherwise, use default.
            if (label == null || label.isEmpty())
                label = getDefaultLabel();
        }
        return label;
    }

    String defaultLabel;

    protected String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
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
            synchronized (LazyAppearance.class) {
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
