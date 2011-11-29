package com.bee32.plover.ox1.dict;

import java.util.ArrayList;
import java.util.List;

import javax.free.TreeNode;

public class PoNode
        implements TreeNode<PoNode> {

    PoNode parent;
    List<PoNode> children = new ArrayList<PoNode>();
    Object key;
    Object data;

    public PoNode() {
    }

    public synchronized void attach(PoNode parent) {
        detach();

        this.parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    public synchronized void detach() {
        if (parent != null)
            parent.removeChild(this);
        parent = null;
    }

    public boolean isVirtual() {
        return data == null;
    }

    public PoNode getParent() {
        return parent;
    }

    public void setParent(PoNode parent) {
        this.parent = parent;
    }

    @Override
    public List<PoNode> getChildren() {
        return children;
    }

    public void addChild(PoNode child) {
        children.add(child);
    }

    public void removeChild(PoNode child) {
        children.remove(child);
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "(" + key + ": " + data + ")";
    }

}
