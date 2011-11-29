package com.bee32.plover.ox1.dict;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class PoTreeBuilderTest
        extends Assert {

    PrefixTreeBuilder buildAlphaTree() {
        PrefixTreeBuilder builder = new PrefixTreeBuilder();
        builder.learn("01");
        builder.learn("0102");
        builder.learn("0103");
        builder.learn("0211");
        builder.learn("0215");
        return builder;
    }

    @Test
    public void testReduce() {
        PrefixTreeBuilder tree = buildAlphaTree();

        System.out.println(tree.dump());

        Set<String> reduced = tree.reduce();
        for (String r : reduced)
            System.out.println("Reduced: " + r);

        System.out.println(tree.dump());
    }

}
