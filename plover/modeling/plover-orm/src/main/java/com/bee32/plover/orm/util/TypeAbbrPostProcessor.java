package com.bee32.plover.orm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.unit.IPersistenceUnitPostProcessor;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class TypeAbbrPostProcessor
        implements IPersistenceUnitPostProcessor, ITypeAbbrAware {

    static Logger logger = LoggerFactory.getLogger(TypeAbbrPostProcessor.class);

    @Override
    public void process(PersistenceUnit unit) {
        for (Class<?> clazz : unit.getClasses()) {

            String abbr = ABBR.register(clazz);

            String simpleName = clazz.getSimpleName();
            if (!simpleName.equals(abbr))
                logger.debug("      Entity " + simpleName + " was compressed to " + abbr);
        }
    }

}
