package com.bee32.plover.ox1.dict;

import java.util.ArrayList;
import java.util.List;

import javax.free.TreeNode;

public class PoNode
        implements TreeNode<PoNode> {

    List<PoNode> children = new ArrayList<PoNode>();

    @Override
    public List<? extends PoNode> getChildren() {
        return children;
    }

    public void addChild(PoNode child) {
        children.add(child);
    }

    public void removeChild(PoNode child) {
        children.remove(child);
    }

}
