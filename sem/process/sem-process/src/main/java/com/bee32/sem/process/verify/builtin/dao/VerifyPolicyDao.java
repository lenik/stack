package com.bee32.sem.process.verify.builtin.dao;

import javax.inject.Inject;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.service.NoVerifyPolicyException;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPrefDao;

public class VerifyPolicyDao
        extends EntityDao<VerifyPolicy, Integer> {

    @Inject
    VerifyPolicyPrefDao prefDao;

    /**
     * 根据实体类型，返回预设的审核策略配置项。
     *
     * @param contextClass
     *            实体类型，非 <code>null</code>。
     * @return 配置的审核策略，如果尚未配置则返回 <code>null</code>。
     */
    public VerifyPolicy getPreferredVerifyPolicy(Class<? extends IVerifyContext> contextClass) {
        if (contextClass == null)
            throw new NullPointerException("entityClass");

        VerifyPolicyPref pref = prefDao.get(contextClass);
        if (pref == null)
            return null;

        VerifyPolicy preferredPolicy = (VerifyPolicy) pref.getPreferredPolicy();
        assert preferredPolicy != null;

        return preferredPolicy;
    }

    public VerifyPolicy getVerifyPolicy(IVerifyContext context) {
        if (context == null)
            throw new NullPointerException("entity");

        Class<? extends IVerifyContext> clazz = context.getClass();
        return getPreferredVerifyPolicy(clazz);
    }

    public VerifyPolicy requirePreferredVerifyPolicy(Class<? extends IVerifyContext> contextClass)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getPreferredVerifyPolicy(contextClass);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

    public VerifyPolicy requireVerifyPolicy(IVerifyContext context)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getVerifyPolicy(context);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

}
