package com.bee32.plover.orm.ext.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;

public abstract class TreeEntityDto<E extends TreeEntity<K, E>, K extends Serializable, $ extends TreeEntityDto<E, K, $>>
        extends UIEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public static final int PARENT = 0x00010000;
    public static final int CHILDREN = 0x00020000;

    $ parent;
    List<$> children;

    public TreeEntityDto() {
        super();
    }

    public TreeEntityDto(int selection) {
        super(selection);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        if (selection.contains(PARENT))
            parent = ($) mref(getClass(), selection.bits, source.getParent());

        if (selection.contains(CHILDREN))
            children = marshalList(getClass(), selection.bits, source.getChildren(), true);
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        if (selection.contains(PARENT))
            merge(target, "parent", parent);

        if (selection.contains(CHILDREN))
            mergeList(target, "children", children);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        K parentId = parseId(map.getString("parent"));

        if (selection.contains(PARENT))
            parent = createAnother().ref(parentId);

        if (selection.contains(CHILDREN)) {
            children = new ArrayList<$>();
            for (TextMap childMap : map.shift("child")) {
                String _childId = childMap.getString("id");
                K childId = parseId(_childId);
                $ child = createAnother().ref(childId);
                children.add(child);
            }
        }
    }

    protected $ createAnother() {
        try {
            return ($) getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate another DTO: " + e.getMessage(), e);
        }
    }

    public $ getParent() {
        return parent;
    }

    public void setParent($ parent) {
        this.parent = parent;
    }

    public List<$> getChildren() {
        return children;
    }

    public void setChildren(List<$> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

}
