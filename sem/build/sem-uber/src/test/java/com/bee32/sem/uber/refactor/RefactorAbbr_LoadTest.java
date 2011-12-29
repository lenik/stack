package com.bee32.sem.uber.refactor;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.bee32.plover.orm.util.EntityUsage;
import com.bee32.plover.orm.util.EntityUsageCollector;
import com.bee32.plover.orm.util.ITypeAbbrAware;

public class RefactorAbbr_LoadTest
        implements ITypeAbbrAware {

    @Test
    public void testLoadFromResources()
            throws ClassNotFoundException, IOException {
        EntityUsageCollector collector = new EntityUsageCollector();
        collector.loadFromResources();
        EntityUsage abbrUsage = collector.getUsage(ITypeAbbrAware.USAGE_ID);
        for (Entry<Class<?>, String> entry : abbrUsage.getEntityMap().entrySet()) {
            Class<?> entityType = entry.getKey();
            String description = entry.getValue();
            System.out.println(entityType.getCanonicalName() + " => " + description);
        }
    }

    @Test
    public void testTypeAbbr_loadUsageMap()
            throws ClassNotFoundException, IOException {
        Map<Class<?>, String[]> usageMap = ABBR.loadUsageMap();
        System.out.println(usageMap);
    }

}
