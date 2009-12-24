package com.seccaproject.plover.arch.ui.impl;

import com.seccaproject.plover.arch.i18n.nls.IPropertySink;
import com.seccaproject.plover.arch.ui.IIconMap;
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
    private IconMapSink iconMapSink;
    private RefdocsSink refdocsSink;

    public UIInfoSink(Class<?> resourceBase) {
        this.iconMapSink = new IconMapSink(resourceBase);
        this.refdocsSink = new RefdocsSink(resourceBase);
    }

    @Override
    public void receive(String name, String content) {
        // TODO Use Java-7 string-switch in future.
        if ("displayName".equals(name))
            displayName = content;
        else if ("description".equals(name))
            description = content;
        else if (name.startsWith("icon."))
            iconMapSink.receive(name.substring(5), content);
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
    public IIconMap getIconMap() {
        return iconMapSink;
    }

    @Override
    public IRefdocs getRefdocs() {
        return refdocsSink;
    }

}
