package com.bee32.sem.process.verify;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

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
     * @param clazz
     *            实体类型，非 <code>null</code>。
     * @return 配置的审核策略，如果尚未配置则返回 <code>null</code>。
     */
    public VerifyPolicy getPreferredVerifyPolicy(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        VerifyPolicyPref pref = prefDao.get(clazz);
        if (pref == null)
            return null;

        VerifyPolicy preferredPolicy = (VerifyPolicy) pref.getPreferredPolicy();
        if (preferredPolicy == null)
            throw new IllegalUsageException("preferredPolicy isn't set in: " + pref);

        return preferredPolicy;
    }

    public VerifyPolicy requirePreferredVerifyPolicy(Class<?> clazz)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getPreferredVerifyPolicy(clazz);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

    public VerifyPolicy getVerifyPolicy(IVerifiable<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        return getPreferredVerifyPolicy(entity.getClass());
    }

    public VerifyPolicy requireVerifyPolicy(IVerifiable<?> entity)
            throws NoVerifyPolicyException {
        VerifyPolicy policy = getVerifyPolicy(entity);
        if (policy == null)
            throw new NoVerifyPolicyException();
        return policy;
    }

}
