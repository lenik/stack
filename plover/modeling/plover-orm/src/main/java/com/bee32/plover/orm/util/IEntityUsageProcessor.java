package com.bee32.plover.orm.util;

public interface IEntityUsageProcessor {

    void process(Class<?> entityType, EntityUsageCollector collector)
            throws Exception;

}
