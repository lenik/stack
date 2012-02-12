package com.bee32.plover.ox1.tree;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TreeEntityUtils {

    public static <D extends TreeEntityDto<?, ?, D>> Set<D> rebuildTree(Map<?, ? extends D> index) {
        Set<D> roots = new LinkedHashSet<D>();
        for (D node : index.values()) {
            D ancestor = node.getParent();
            while (ancestor != null && !ancestor.isNull()) {
                if (index.containsKey(ancestor.getId()))
                    break;
                ancestor = ancestor.getParent();
            }
            Object ancestorId = ancestor == null ? null : ancestor.getId();
            if (ancestorId == null) {
                roots.add(node);
            } else {
                D _ancestor = index.get(ancestorId);
                _ancestor.addChild(node);
            }
        }
        return roots;
    }

}
