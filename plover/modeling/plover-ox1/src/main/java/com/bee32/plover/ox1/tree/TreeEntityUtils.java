package com.bee32.plover.ox1.tree;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TreeEntityUtils {

    public static <D extends TreeEntityDto<?, ?, D>> //
    Set<D> rebuildTree(Collection<? extends D> nodes, Map<?, ? extends D> index) {
        Set<D> roots = new LinkedHashSet<D>();
        for (D node : nodes) {
            D _parent = node.getParent();
            Object parentId = _parent == null ? null : _parent.getId();
            if (parentId == null) {
                roots.add(node);
            } else {
                D parent = index.get(parentId);
                parent.addChild(node);
            }
        }
        return roots;
    }

}
