package com.bee32.sem.uber.refactor;

import org.junit.Assert;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.EntityUsageCollector;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.uber.SEMUberModuleTest;

public class RefactorAbbr_UpdateResources
        extends Assert
        implements ITypeAbbrAware {

    static PersistenceUnit unit = UsingUtil.getUsingUnit(SEMUberModuleTest.class);

    public static void main(String[] args)
            throws Exception {
        EntityUsageCollector collector = new EntityUsageCollector();
        collector.addProcessor(new AbbrUsageProcessor());
        collector.collect(unit);
        collector.writeToMavenResources();
    }

}
