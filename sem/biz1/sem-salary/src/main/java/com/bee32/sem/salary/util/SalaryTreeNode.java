package com.bee32.sem.salary.util;

import java.io.Serializable;
import java.util.List;

public class SalaryTreeNode
        implements Serializable {

    private static final long serialVersionUID = 1L;
    String label;
    int order;
    List<SalaryTreeNode> children;

    public SalaryTreeNode getChild(String label) {
        // TODO
        return null;
    }

    public SalaryTreeNode getParent() {
        // TODO
        return null;
    }

    public void addChild(SalaryTreeNode child) {
        int child_order = child.getOrder();
        if (child_order < this.order)
            this.order = child_order;
        children.add(child);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<SalaryTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SalaryTreeNode> children) {
        this.children = children;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        SalaryTreeNode o = (SalaryTreeNode) obj;
        if (label == null && o.getLabel() == null)
            return true;
        if (label == null && o.getLabel() != null)
            return false;
        if (label != null && o.getLabel() == null)
            return false;
        if (label.equals(o.getLabel()))
            return true;
        return false;
    }

}
