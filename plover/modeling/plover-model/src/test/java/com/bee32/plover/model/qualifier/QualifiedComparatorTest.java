package com.bee32.plover.model.qualifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class QualifiedComparatorTest
        extends Assert {

    List<Qualified> orig;

    Qualified a;
    Qualified b;
    Qualified c;
    Qualified d;

    public QualifiedComparatorTest() {
        a = new Qualified();
        QualifierMap aMap = a.getQualifierMap();

        aMap.addQualifier(new Group("b", 1));
        aMap.addQualifier(PriorityTest.low);
        aMap.addQualifier(new Group("foo", 20));
        aMap.addQualifier(PriorityTest.high);
        aMap.addQualifier(new Group("a", 1));

        b = new Qualified(); // b > a
        QualifierMap bMap = b.getQualifierMap();

        bMap.addQualifier(PriorityTest.low);
        bMap.addQualifier(new Group("foo", 20));

        c = new Qualified(); // c == a
        QualifierMap cMap = c.getQualifierMap();

        cMap.addQualifier(new Group("foo", 20));
        cMap.addQualifier(PriorityTest.low);
        cMap.addQualifier(new Group("a", 1));
        cMap.addQualifier(new Group("b", 1));
        cMap.addQualifier(PriorityTest.high);

        d = new Qualified();
        QualifierMap dMap = d.getQualifierMap();

        dMap.addQualifier(new Group("foo", 20));
        dMap.addQualifier(PriorityTest.low);
        dMap.addQualifier(new Group("a", 0)); // d < c
        dMap.addQualifier(new Group("b", 1));
        dMap.addQualifier(PriorityTest.high);

        // ORDER: d a c b
        // ORDER: d c a b
        orig = new ArrayList<Qualified>();
        orig.add(a);
        orig.add(b);
        orig.add(c);
        orig.add(d);
    }

    QualifiedComparator cmp = QualifiedComparator.getInstance();

    @Test
    public void testSimple() {
        assertTrue(cmp.compare(a, b) < 0);
        assertTrue(cmp.compare(b, c) > 0);
        assertTrue(cmp.compare(a, c) == 0);
        assertTrue(cmp.compare(c, d) > 0);
    }

    @Test
    public void testOrder() {
        List<Qualified> copy = new ArrayList<Qualified>(orig);
        Collections.sort(copy, cmp);
        Qualified c0 = copy.get(0);
        Qualified c1 = copy.get(1);
        Qualified c2 = copy.get(2);
        Qualified c3 = copy.get(3);

        assertSame(d, c0);
        assertTrue(c1 == a || c1 == c);
        assertTrue(c2 == a || c2 == c);
        assertSame(b, c3);
    }

}
