package com.bee32.plover.ajax.util;

import java.util.Map;

public abstract class AbstractJsTreeNode
        implements IJsTreeNode {

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<?, ?> getAttributes() {
        return null;
    }

    @Override
    public String getIcon() {
        return FOLDER_ICON;
    }

    @Override
    public boolean isExpanded() {
        return true;
    }

}
