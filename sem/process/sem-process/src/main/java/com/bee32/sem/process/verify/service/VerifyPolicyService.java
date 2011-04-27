package com.bee32.sem.process.verify.service;

import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;
import com.bee32.sem.process.verify.util.VerifiableEntityAccessor;
import com.bee32.sem.process.verify.util.VerifiableEntityBean;

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
        VerifyPolicy<C> preferredVerifyPolicy = policyDao.getVerifyPolicy(entity);
        return new VerifyPolicyDto().marshal(preferredVerifyPolicy);
    }

    // --o IVerifyPolicy.

    @Override
    public Class<IVerifyContext> getRequiredContext() {
        return IVerifyContext.class;
    }

    /**
     * 表上的 {@link VerifiableEntityBean#isVerified() verified} 是缓存的值，仅用于检索用途。
     * <p>
     * 当需要在业务过程中判断对象是否已审核时，需要调用本方法重新计算审核结果。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(VerifiableEntityBean)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @return <code>true</code> 表示成功审核，否则
     * @see #verify(VerifiableEntityBean)
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isVerified(IVerifyContext context) {
        VerifyPolicy<IVerifyContext> verifyPolicy = policyDao.getVerifyPolicy(context);
        if (verifyPolicy == null)
            return false;
        return verifyPolicy.isVerified(context);
    }

    /**
     * 重新计算审核状态，除非审核成功否则抛出异常。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(VerifiableEntityBean)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @throws VerifyException
     *             当审核不成功时。
     * @see #verify(VerifiableEntityBean)
     */
    @Transactional(readOnly = true)
    @Override
    public void assertVerified(IVerifyContext context)
            throws VerifyException {
        VerifyPolicy<IVerifyContext> verifyPolicy = policyDao.requireVerifyPolicy(context);
        verifyPolicy.assertVerified(context);
    }

    /**
     * 重新计算审核状态，并返回详细的审核结果。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(VerifiableEntityBean)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @return {@link VerifyResult} 详细的审核结果。
     * @see #verify(VerifiableEntityBean)
     */
    @Transactional(readOnly = true)
    @Override
    public VerifyResult verify(IVerifyContext context) {
        if (context == null)
            throw new NullPointerException("context");

        VerifyPolicy<IVerifyContext> verifyPolicy = policyDao.getVerifyPolicy(context);

        VerifyResult result;
        if (verifyPolicy == null)
            result = VerifyResult.n_a("不可用");
        else
            result = verifyPolicy.verify(context);

        return result;
    }

    /**
     * 重新计算审核状态并更新缓存，产生审核请求事件或更新事件状态，以及发送审核通知函给相关责任人。
     *
     * 您需要自己计算
     *
     * @param entity
     *            被计算并更新审核状态的实体，非 <code>null</code>。
     * @return 详细的审核结果。
     */
    @Transactional
    public <C extends IVerifyContext> VerifyResult verifyEntity(VerifiableEntityBean<? extends Number, C> entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        C context = entity.getVerifyContext();

        VerifyResult result = verify(context);

        VerifiableEntityAccessor.setVerifyState(entity, result.getState());
        VerifiableEntityAccessor.setVerifyError(entity, result.getMessage());

        Task verifyTask = entity.getVerifyTask();
        {
            if (verifyTask == null) {
                verifyTask = new Task();
                entity.setVerifyTask(verifyTask);
            }

            verifyTask.setCategory(VerifyPolicy.class);
            verifyTask.setPriority(EventPriority.HIGH);

            verifyTask.setClosed(result.getState().isClosed());
            verifyTask.setActor(null); // session current user.

            String subject = "Please verify";
            String message = "message";

            verifyTask.setSubject(subject);
            verifyTask.setMessage(message);
            verifyTask.setBeginTime(new Date());
            // verifyTask.setEndTime(entity.getVerifiedDate());

            verifyTask.setRef(entity);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IVerifyContext contextEntity) {
        VerifyPolicy<IVerifyContext> preferredVerifyPolicy = policyDao.getVerifyPolicy(contextEntity);
        return preferredVerifyPolicy.getDeclaredResponsibles(contextEntity);
    }

}
