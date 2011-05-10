package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.inject.Inject;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;

@Deprecated
public class Verifiable2EntityDao<E extends Verifiable2Entity<K, C>, K extends Serializable, C extends IVerifyContext>
        extends EntityDao<E, K> {

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    @Override
    protected E _preinit(E entity) {
        VerifyPolicy<C> verifyPolicy = (VerifyPolicy<C>) verifyPolicyDao.getVerifyPolicy(entity);

        entity.setVerifyPolicy(verifyPolicy);

        return entity;
    }

}
