package com.bee32.sem.chance;

import javax.free.IdentityHashSet;
import javax.free.Strings;

import com.bee32.plover.orm.sample.SamplePackage;
import com.bee32.plover.orm.sample.SuperSamplePackage.Normals;
import com.bee32.plover.test.WiredTestCase;

public class SEMChanceSamplesTest
        extends WiredTestCase {

    // @Test
    public void testDump() {
        walk(application.getBean(Normals.class), 0);
    }

    static IdentityHashSet loaded = new IdentityHashSet();

    static void walk(SamplePackage pack, int level) {
        String prefix = Strings.repeat(level * 4, ' ');

        if (pack == null)
            throw new NullPointerException("pack");

        if (!loaded.add(pack))
            return;

        System.out.println(prefix + "Load " + pack.getName());

        for (SamplePackage dependency : pack.getDependencies())
            walk(dependency, level + 1);

        pack.endLoad();
    }

}
