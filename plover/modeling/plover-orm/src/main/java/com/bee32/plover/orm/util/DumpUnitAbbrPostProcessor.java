package com.bee32.plover.orm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.unit.AbstractPersistenceUnitPostProcessor;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class DumpUnitAbbrPostProcessor
        extends AbstractPersistenceUnitPostProcessor
        implements ITypeAbbrAware {

    static Logger logger = LoggerFactory.getLogger(DumpUnitAbbrPostProcessor.class);

    @Override
    public void process(PersistenceUnit unit) {
        for (Class<?> clazz : unit.getClasses()) {
            String abbr = ABBR.abbr(clazz); // Dont register it anymore.
            String simpleName = clazz.getSimpleName();
            if (!simpleName.equals(abbr))
                logger.info("      Entity name " + simpleName + " was compressed to " + abbr);
        }
    }

}
