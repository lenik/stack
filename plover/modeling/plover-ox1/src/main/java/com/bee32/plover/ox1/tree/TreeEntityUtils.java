package com.bee32.plover.ox1.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.orm.util.EntityDto;

public class TreeEntityUtils {

    public static <D extends TreeEntityDto<?, K, D>, K extends Serializable> //
    Set<D> rebuildTree(Collection<? extends D> nodes, Map<K, D> index) {
        Set<D> roots = new LinkedHashSet<D>();
        for (D node : nodes) {
            D _parent = node.getParent();
            K parentId = _parent == null ? null : _parent.getId();
            if (parentId == null) {
                roots.add(node);
            } else {
                D parent = index.get(parentId);
                List<D> siblings = parent.getChildren();
                if (!siblings.contains(node))
                    siblings.add(node);
            }
        }
        return roots;
    }

    public static <D extends EntityDto<?, ?>, K extends Serializable> Map<K, D> index(Collection<? extends D> dtos) {
        Map<K, D> map = new LinkedHashMap<K, D>();
        for (D dto : dtos) {
            K id = (K) dto.getId();
            map.put(id, dto);
        }
        return map;
    }

}
