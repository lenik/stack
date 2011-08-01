package com.bee32.sem.chance;

import javax.free.IdentityHashSet;
import javax.free.Strings;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.orm.util.DiamondPackage;
import com.bee32.plover.orm.util.ImportSamplesUtil;
import com.bee32.plover.orm.util.SamplePackage;
import com.bee32.plover.test.WiredTestCase;

public class SEMChanceSamplesTest
        extends WiredTestCase {

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        ImportSamplesUtil.applyImports(applicationContext);
    }

    // @Test
    public void testDump() {
        walk(DiamondPackage.NORMAL, 0);
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
