package com.bee32.plover.arch.ui.res;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.util.res.IPropertyAcceptor;

/**
 * 从资源中提取的用户界面信息。
 */
public class InjectedAppearance
        implements IAppearance, IPropertyAcceptor {

    private String displayName;
    private String description;
    private InjectedImageMap imageMap;
    private InjectedRefdocs refdocs;

    public InjectedAppearance(Class<?> resourceBase) {
        if (resourceBase == null)
            throw new NullPointerException("resourceBase");
        this.imageMap = new InjectedImageMap(resourceBase);
        this.refdocs = new InjectedRefdocs(resourceBase);
    }

    @Override
    public void receive(String name, String content) {
        if ("displayName".equals(name))
            displayName = content;

        else if ("description".equals(name))
            description = content;

        else if ("icon".equals(name))
            imageMap.receive("", content);

        else if (name.startsWith("icon."))
            imageMap.receive(name.substring(5), content);

        else if (name.startsWith("ref."))
            refdocs.receive(name.substring(4), content);
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
        return imageMap;
    }

    @Override
    public IRefdocs getRefdocs() {
        return refdocs;
    }

}
