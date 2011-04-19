package com.bee32.sem.process.verify.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.unit.IPersistenceUnitPostProcessor;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;

public class ProcessDefaultsPostProcessor
        implements IPersistenceUnitPostProcessor {

    static Logger logger = LoggerFactory.getLogger(ProcessDefaultsPostProcessor.class);

    @Override
    public void process(PersistenceUnit unit) {
        Set<Class<?>> classes = unit.getClasses();
        for (Class<?> entityClass : classes) {
            if (IVerifiable.class.isAssignableFrom(entityClass)) {

                Class<? extends IVerifyContext> contextClass = ClassUtil.infer1(entityClass, IVerifiable.class, 0);

                VerifyPolicyPref verifyPolicyPref = new VerifyPolicyPref();
                verifyPolicyPref.setEntityClass(entityClass);

                VerifyPolicyManager.forContext(contextClass);

            }
        }
    }

}
