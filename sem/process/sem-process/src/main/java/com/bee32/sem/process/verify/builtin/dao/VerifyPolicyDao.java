package com.bee32.sem.process.verify.builtin.dao;

import javax.inject.Inject;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPref;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPrefDao;

public class VerifyPolicyDao
        extends EntityDao<VerifyPolicy<?>, Integer> {

    @Inject
    VerifyPolicyPrefDao prefDao;

    /**
     * 根据实体类型，返回预设的审核策略配置项。
     *
     * @param entityClass
     *            实体类型，非 <code>null</code>。
     * @return 配置的审核策略，如果尚未配置则返回 <code>null</code>。
     */
    public <C extends IVerifyContext> VerifyPolicy<C> getPreferredVerifyPolicy(Class<C> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");

        VerifyPolicyPref pref = prefDao.get(entityClass);
        if (pref == null)
            return null;

        VerifyPolicy<C> preferredPolicy = (VerifyPolicy<C>) pref.getPreferredPolicy();
        assert preferredPolicy != null;

        return preferredPolicy;
    }

    public <C extends IVerifyContext> VerifyPolicy<C> getPreferredVerifyPolicy(C entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        Class<C> clazz = (Class<C>) entity.getClass();
        return getPreferredVerifyPolicy(clazz);
    }

}
