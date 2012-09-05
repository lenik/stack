package com.bee32.sem.salary.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalaryTreeNode {

    SalaryTreeNode parent;
    String label;
    List<SalaryTreeNode> children = new ArrayList<SalaryTreeNode>();
    int order;

    public SalaryTreeNode getChild(String label) {
        for (SalaryTreeNode node : children) {
            if (node.getLabel().equals(label))
                return node;
        }
        return null;
    }

    public SalaryTreeNode getParent() {
        return parent;
    }

    public void setParent(SalaryTreeNode parent) {
        this.parent = parent;
    }

    public boolean addChild(SalaryTreeNode child) {

        for (SalaryTreeNode node : children)
            if (node.getLabel().equals(child.getLabel()))
                return false;
        children.add(child);
        child.setParent(this);
        return true;
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

    public void updateOrder() {
        int min = 0;
        if (!children.isEmpty()) {
            for (SalaryTreeNode child : children) {
                child.updateOrder();
                int order = child.getOrder();
                if (min > order)
                    min = order;
            }
            this.order = min;
        }
    }

    public List<SalaryTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SalaryTreeNode> children) {
        this.children = children;
    }

    public SalaryTreeNode resolve(String path) {
        if (path.isEmpty())
            return this;

        String element = null;
        String remaining = null;
        int index = path.indexOf("/");
        if (index >= 0) {
            element = path.substring(0, index);
            remaining = path.substring(index + 1);
        } else {
            element = path;
            remaining = null;
        }

        SalaryTreeNode node = this.getChild(element);
        if (node == null) {
            node = new SalaryTreeNode();
            node.setLabel(element);
            this.addChild(node);
        }

        if (remaining != null)
            return node.resolve(remaining);
        else
            return node;
    }

    public int[] getOrderArray() {
        List<Integer> tmp = new ArrayList<Integer>();
        SalaryTreeNode node = this;
        while (node != null) {
            tmp.add(node.getOrder());
            node = node.getParent();
        }
        int[] array = new int[tmp.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = tmp.get(array.length - i - 1);
        return array;
    }

    public String[] getLabelArray() {
        List<String> tmp = new ArrayList<String>();
        SalaryTreeNode node = this;
        while (node != null) {
            tmp.add(node.getLabel());
            node = node.getParent();
        }
        Collections.reverse(tmp);
        return tmp.toArray(new String[0]);
    }

}
