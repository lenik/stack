package com.bee32.plover.ox1.tree;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class TreeEntityTest
        extends Assert {

    static class Node
            extends TreeEntityAuto<Integer, Node> {

        private static final long serialVersionUID = 1L;

        public Node(String name) {
            this(null, name);
        }

        public Node(Node parent, String name) {
            super(parent);
            this.name = name;
        }

        public String getText() {
            return getGraphPrefix() + " " + name;
        }

    }

    @SuppressWarnings("unused")
    @Test
    public void testGraphTree()
            throws IOException {
        Node root = new Node("root");
        Node parent1 = new Node(root, "parent1");
        Node child1 = new Node(parent1, "child1");
        Node child2 = new Node(parent1, "child2");
        Node parent2 = new Node(root, "parent2");
        Node child3 = new Node(parent2, "child3");
        Node child4 = new Node(parent2, "child4");
        root.dump();
    }

}
