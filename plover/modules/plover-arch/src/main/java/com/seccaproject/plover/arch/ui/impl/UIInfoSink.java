package com.seccaproject.plover.arch.ui.impl;

import com.seccaproject.plover.arch.i18n.nls.IPropertySink;
import com.seccaproject.plover.arch.ui.IImageMap;
import com.seccaproject.plover.arch.ui.IRefdocs;
import com.seccaproject.plover.arch.ui.IUIInfo;

/**
 * 从资源中提取的用户界面信息。
 *
 * @test {@link UIInfoSinkTest}
 */
public class UIInfoSink
        implements IUIInfo, IPropertySink {

    private String displayName;
    private String description;
    private ImageMapSink imageMapSink;
    private RefdocsSink refdocsSink;

    public UIInfoSink(Class<?> resourceBase) {
        if (resourceBase == null)
            // resourceBase = getClass();
            throw new NullPointerException("resourceBase");
        this.imageMapSink = new ImageMapSink(resourceBase);
        this.refdocsSink = new RefdocsSink(resourceBase);
    }

    @Override
    public void receive(String name, String content) {
        // TODO Use Java-7 string-switch in future.
        if ("displayName".equals(name))
            displayName = content;
        else if ("description".equals(name))
            description = content;
        else if ("icon".equals(name))
            imageMapSink.receive("", content);
        else if (name.startsWith("icon."))
            imageMapSink.receive(name.substring(5), content);
        else if (name.startsWith("ref."))
            refdocsSink.receive(name.substring(4), content);
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IImageMap getImageMap() {
        return imageMapSink;
    }

    @Override
    public IRefdocs getRefdocs() {
        return refdocsSink;
    }

}
