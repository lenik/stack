package com.bee32.plover.arch.ui.res;

import java.net.URL;
import java.util.Locale;

import javax.inject.Inject;

import com.bee32.plover.arch.ui.LazyAppearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.ClassResourceProperties;
import com.bee32.plover.arch.util.res.IPropertyAcceptor;
import com.bee32.plover.arch.util.res.IPropertyDispatcher;
import com.bee32.plover.arch.util.res.IPropertyDispatcherAware;
import com.bee32.plover.arch.util.res.IPropertyRefreshListener;
import com.bee32.plover.arch.util.res.PropertyDispatcher;
import com.bee32.plover.arch.util.res.PropertyRefreshEvent;

/**
 * 从资源中提取的用户界面信息。
 */
public class InjectedAppearance
        extends LazyAppearance
        implements IPropertyAcceptor, IPropertyDispatcherAware, IPropertyRefreshListener {

    private String label;
    private String description;
    private InjectedImageMap imageMap;
    private InjectedRefdocs refdocs;

    private IPropertyDispatcher propertyDispatcher;
    private URL contextURL;

    /**
     * Don't forget to {@link #setPropertyDispatcher(IPropertyDispatcher) set the property
     * dispatcher}.
     */
    public InjectedAppearance(IAppearance parent, URL contextURL) {
        super(parent);
        this.contextURL = contextURL;
    }

    /**
     * Construct an injected appearance for class-local usage, using the specified locale.
     */
    public InjectedAppearance(Class<?> declaringClass, Locale locale) {
        super(null);
        this.contextURL = ClassUtil.getContextURL(declaringClass);

        ClassResourceProperties properties = new ClassResourceProperties(declaringClass, locale);
        IPropertyDispatcher propertyDispatcher = new PropertyDispatcher(properties);

        propertyDispatcher.setRootAcceptor(this);
        this.setPropertyDispatcher(propertyDispatcher);
    }

    @Override
    public IPropertyDispatcher getPropertyDispatcher() {
        return propertyDispatcher;
    }

    @Override
    public void setPropertyDispatcher(IPropertyDispatcher dispatcher) {
        this.propertyDispatcher = dispatcher;
    }

    @Override
    public void propertyRefresh(PropertyRefreshEvent event) {
        super.invalidate();
    }

    @Override
    public void receive(String name, String content) {
        if ("label".equals(name))
            label = content;

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
                    imageMap = new InjectedImageMap(contextURL);
                }
            }
        }
        return imageMap;
    }

    InjectedRefdocs _refdocs() {
        if (refdocs == null) {
            synchronized (this) {
                if (refdocs == null) {
                    refdocs = new InjectedRefdocs(contextURL);
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
    protected String loadLabel() {
        _propertyDispatcher().pull();
        return label;
    }

    @Override
    protected String loadDescription() {
        _propertyDispatcher().pull();
        return description;
    }

    @Inject
    protected IImageMap loadImageMap() {
        _propertyDispatcher().pull();
        return imageMap;
    }

    @Inject
    protected IRefdocs loadRefdocs() {
        _propertyDispatcher().pull();
        return refdocs;
    }

}
