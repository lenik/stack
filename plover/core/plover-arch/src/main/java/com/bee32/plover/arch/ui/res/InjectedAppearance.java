package com.bee32.plover.arch.ui.res;

import javax.inject.Inject;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.util.res.ClassResourceResolver;
import com.bee32.plover.arch.util.res.IPropertyAcceptor;
import com.bee32.plover.arch.util.res.IPropertyDispatcher;
import com.bee32.plover.arch.util.res.IPropertyDispatcherAware;
import com.bee32.plover.arch.util.res.IResourceResolver;

/**
 * 从资源中提取的用户界面信息。
 */
public class InjectedAppearance
        extends Appearance
        implements IPropertyAcceptor, IPropertyDispatcherAware {

    private String displayName;
    private String description;
    private InjectedImageMap imageMap;
    private InjectedRefdocs refdocs;

    private IResourceResolver resourceResolver;

    private IPropertyDispatcher propertyDispatcher;

    public InjectedAppearance(Object target, IAppearance parent, Class<?> declaringClass) {
        this(target, parent, new ClassResourceResolver(declaringClass));
    }

    public InjectedAppearance(Object target, IAppearance parent, IResourceResolver resourceResolver) {
        super(target, parent);

        if (resourceResolver == null)
            throw new NullPointerException("resourceResolver");

        this.resourceResolver = resourceResolver;
    }

    @Override
    public void setPropertyDispatcher(IPropertyDispatcher propertyDispatcher) {
        this.propertyDispatcher = propertyDispatcher;
    }

    @Override
    public void receive(String name, String content) {
        if ("displayName".equals(name))
            displayName = content;

        else if ("description".equals(name))
            description = content;

        else if ("icon".equals(name))
            _imageMap().receive("", content);

        else if (name.startsWith("icon."))
            _imageMap().receive(name.substring(5), content);

        else if (name.startsWith("ref."))
            _refdocs().receive(name.substring(4), content);
    }

    InjectedImageMap _imageMap() {
        if (imageMap == null) {
            synchronized (this) {
                if (imageMap == null) {
                    imageMap = new InjectedImageMap(resourceResolver);
                }
            }
        }
        return imageMap;
    }

    InjectedRefdocs _refdocs() {
        if (refdocs == null) {
            synchronized (this) {
                if (refdocs == null) {
                    refdocs = new InjectedRefdocs(resourceResolver);
                }
            }
        }
        return refdocs;
    }

    IPropertyDispatcher _propertyDispatcher() {
        if (propertyDispatcher == null)
            throw new IllegalStateException("Property dispatcher is not bound.");
        return propertyDispatcher;
    }

    @Override
    protected String loadDisplayName() {
        _propertyDispatcher().require();
        return displayName;
    }

    @Override
    protected String loadDescription() {
        _propertyDispatcher().require();
        return description;
    }

    @Inject
    protected IImageMap loadImageMap() {
        _propertyDispatcher().require();
        return imageMap;
    }

    @Inject
    protected IRefdocs loadRefdocs() {
        _propertyDispatcher().require();
        return refdocs;
    }

    @Override
    public String toString() {
        Object target = getTarget();
        return resourceResolver + "<" + target + ">";
    }

}
