package com.bee32.sem.asset.service;

import com.bee32.plover.ox1.dict.CodeTreeBuilder;

public class SumTree
        extends CodeTreeBuilder {

    public SumTree() {
        setNodeClass(SumNode.class);
        setFormatter(SumNode.SumNodeFormatter.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SumNode getRoot() {
        return super.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public SumNode getNode(String key) {
        return super.getNode(key);
    }

}
