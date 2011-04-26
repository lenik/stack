package com.bee32.sem.process.verify.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

public class VerifyPolicyService
        extends EnterpriseService {

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

}
