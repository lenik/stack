package com.bee32.plover.ajax.util;

import java.util.List;
import java.util.Map;

public interface IJsTreeNode {

    String FOLDER_ICON = "folder";

    String getUrl();

    String getTitle();

    Map<?, ?> getAttributes();

    /**
     * if `icon` contains a slash / it is treated as a file, used for background otherwise - it is
     * added as a class to the <ins> node
     */
    String getIcon();

    boolean isExpanded();

    List<IJsTreeNode> getChildren();

}
