package com.bee32.plover.inject.util;

import javax.free.IPreorder;

import org.junit.Assert;
import org.junit.Test;

public class NameQualifiedClassPreorderTest
        extends Assert
        implements NQCLiterals {

    @Test
    public void testQualifiers() {
        int cmp = preorder.precompare(fooList, barList);
        assertEquals(IPreorder.UNKNOWN, cmp);

        cmp = preorder.precompare(fooList, foobarList);
        assertEquals(IPreorder.LESS_THAN, cmp);

        cmp = preorder.precompare(foobarList, fooList);
        assertEquals(IPreorder.GREATER_THAN, cmp);
    }

    @Test
    public void testTypeOrder() {
        int cmp = preorder.precompare(fooList, fooArrayList);
        assertEquals(IPreorder.LESS_THAN, cmp);

        cmp = preorder.precompare(fooList, foobarArrayList);
        assertEquals(IPreorder.LESS_THAN, cmp);

        cmp = preorder.precompare(foobarArrayList, fooList);
        assertEquals(IPreorder.GREATER_THAN, cmp);
    }

    @Test
    public void testMixedQualifierAndType() {
        int cmp = preorder.precompare(fooList, foobarArrayList);
        assertEquals(IPreorder.LESS_THAN, cmp);

        cmp = preorder.precompare(foobarArrayList, fooList);
        assertEquals(IPreorder.GREATER_THAN, cmp);

        cmp = preorder.precompare(foobarList, fooArrayList);
        assertEquals(IPreorder.LESS_THAN, cmp);
        // assertEquals(IPreorder.UNKNOWN, cmp);
    }

}
