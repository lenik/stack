package com.bee32.sem.process.verify.service;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

public class VerifyPolicyService
        extends EnterpriseService
        implements IVerifyPolicy<IVerifyContext> {

    @Inject
    VerifyPolicyDao policyDao;

    @Transactional(readOnly = true)
    public <C extends IVerifyContext> VerifyPolicyDto getPreferredVerifyPolicy(Class<C> entityClass) {
        VerifyPolicy<C> preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entityClass);
        return new VerifyPolicyDto().marshal(preferredVerifyPolicy);
    }

    @Transactional(readOnly = true)
    public <C extends IVerifyContext> VerifyPolicyDto getPreferredVerifyPolicy(C entity) {
        VerifyPolicy<C> preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entity);
        return new VerifyPolicyDto().marshal(preferredVerifyPolicy);
    }

    // --o IVerifyPolicy.

    @Override
    public Class<IVerifyContext> getRequiredContext() {
        return IVerifyContext.class;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isVerified(IVerifyContext contextEntity) {
        VerifyPolicy<IVerifyContext> preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(contextEntity);
        return preferredVerifyPolicy.isVerified(contextEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public void verify(IVerifyContext contextEntity)
            throws VerifyException {
        VerifyPolicy<IVerifyContext> preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(contextEntity);
        preferredVerifyPolicy.verify(contextEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IVerifyContext contextEntity) {
        VerifyPolicy<IVerifyContext> preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(contextEntity);
        return preferredVerifyPolicy.getDeclaredResponsibles(contextEntity);
    }

}
