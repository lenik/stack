package com.bee32.sem.process.verify;

import javax.inject.Inject;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;
import com.bee32.sem.process.verify.preference.VerifyPolicyPrefDao;
import com.bee32.sem.process.verify.service.NoVerifyPolicyException;

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
    public VerifyPolicy getPreferredVerifyPolicy(Class<?> contextClass) {
        if (contextClass == null)
            throw new NullPointerException("entityClass");

        VerifyPolicyPref pref = prefDao.get(contextClass);
        if (pref == null)
            return null;

        VerifyPolicy preferredPolicy = (VerifyPolicy) pref.getPreferredPolicy();
        assert preferredPolicy != null;

        return preferredPolicy;
    }

    public VerifyPolicy requirePreferredVerifyPolicy(Class<?> contextClass)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getPreferredVerifyPolicy(contextClass);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

    public <E extends Entity<?> & IVerifiable<?>> VerifyPolicy getVerifyPolicy(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        Object context = entity.getVerifyContext();
        Class<?> contextClass = context.getClass();
        return getPreferredVerifyPolicy(contextClass);
    }

    public <E extends Entity<?> & IVerifiable<?>> VerifyPolicy requireVerifyPolicy(E entity)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getVerifyPolicy(entity);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

}
