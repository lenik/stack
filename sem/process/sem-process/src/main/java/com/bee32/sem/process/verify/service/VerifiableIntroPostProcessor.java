package com.bee32.sem.process.verify.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.unit.AbstractPersistenceUnitPostProcessor;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyPolicyManager;

/**
 * 审核信息后置处理器
 *
 * <p lang="en">
 */
public class VerifiableIntroPostProcessor
        extends AbstractPersistenceUnitPostProcessor {

    static Logger logger = LoggerFactory.getLogger(VerifiableIntroPostProcessor.class);

    @Override
    public synchronized void process(PersistenceUnit unit) {
        Set<Class<?>> classes = unit.getClasses();
        for (Class<?> entityClass : classes) {
            if (IVerifiable.class.isAssignableFrom(entityClass))
                VerifyPolicyManager.addVerifiableType(entityClass);
        }
    }

}
