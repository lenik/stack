package com.bee32.sem.process.verify.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.VerifyState;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.util.AbstractVerifyContext;
import com.bee32.sem.process.verify.util.VerifyContextAccessor;

public class VerifyService
        extends DataService
        implements IVerifyPolicy {

    @Inject
    VerifyPolicyDao policyDao;

    @Transactional(readOnly = true)
    public VerifyPolicyDto getPreferredVerifyPolicy(Class<? extends IVerifyContext> entityClass) {
        VerifyPolicy preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entityClass);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, preferredVerifyPolicy);
        // return new VerifyPolicyDto(preferredVerifyPolicy);
        return policyDto;
    }

    @Transactional(readOnly = true)
    public VerifyPolicyDto getVerifyPolicy(IVerifiable<?> entity) {
        IVerifyContext verifyContext = entity.getVerifyContext();
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(verifyContext);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, verifyPolicy);
        return policyDto;
    }

    // --o IVerifyPolicy.

    @Override
    public Class<IVerifyContext> getRequiredContext() {
        return IVerifyContext.class;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifiable<?> entity) {
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(contextEntity);

        if (verifyPolicy == null)
            return new HashSet<Principal>();

        return verifyPolicy.getDeclaredResponsibles(contextEntity);
    }

    /**
     * 表上的 {@link AbstractVerifyContext#isVerified() verified} 是缓存的值，仅用于检索用途。
     * <p>
     * 当需要在业务过程中判断对象是否已审核时，需要调用本方法重新计算审核结果。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(AbstractVerifyContext)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @return <code>true</code> 表示成功审核，否则
     * @see #verify(AbstractVerifyContext)
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isVerified(IVerifyContext context) {
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(context);
        if (verifyPolicy == null)
            return false;
        return verifyPolicy.isVerified(context);
    }

    /**
     * 重新计算审核状态，除非审核成功否则抛出异常。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(AbstractVerifyContext)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @throws VerifyException
     *             当审核不成功时。
     * @see #verify(AbstractVerifyContext)
     */
    @Transactional(readOnly = true)
    @Override
    public void assertVerified(IVerifyContext context)
            throws VerifyException {
        VerifyPolicy verifyPolicy = policyDao.requireVerifyPolicy(context);
        verifyPolicy.assertVerified(context);
    }

    /**
     * 重新计算审核状态，并返回详细的审核结果。
     *
     * 本方法不会更新缓存，如果你需要更新缓存请调用 {@link #verify(AbstractVerifyContext)} 方法。
     *
     * @param context
     *            要计算审核状态的实体对象。
     * @return {@link VerifyResult} 详细的审核结果。
     * @see #verify(AbstractVerifyContext)
     */
    @Transactional(readOnly = true)
    @Override
    public VerifyResult verify(IVerifyContext context) {
        if (context == null)
            throw new NullPointerException("context");

        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(context);

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
    public <E extends Entity<?> & IVerifiable<C>, C extends IVerifyContext> //
    VerifyResult verifyEntity(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        User __currentUser = SessionUser.getInstance().getInternalUser();

        // XXX
        // userService.get(0, __currentUser.getId());

        C _context = entity.getVerifyContext();

        // Do verification.
        VerifyResult result = verify(_context);

        AbstractVerifyContext context = (AbstractVerifyContext) _context;
        VerifyContextAccessor.setVerifyState(context, result.getState());
        VerifyContextAccessor.setVerifyError(context, result.getMessage());
        VerifyContextAccessor.setVerifyEvalDate(context, new Date());

        Task verifyTask = context.getVerifyTask();
        {
            if (verifyTask == null) {
                verifyTask = new Task();
                context.setVerifyTask(verifyTask);
            }

            verifyTask.setSourceClass(VerifyPolicy.class);
            verifyTask.setPriority(EventPriority.HIGH);

            VerifyState state = result.getState();
            verifyTask.setClosed(state.isClosed());
            verifyTask.setState(state);

            verifyTask.setActor(null); // session current user.

            String entityName = ClassUtil.getDisplayName(entity.getClass()) + " [" + entity.getId() + "]";

            String subject = "【作业跟踪】【审核】" + entityName;
            String message = "（无可用内容）";

            verifyTask.setSubject(subject);
            verifyTask.setMessage(message);
            verifyTask.setBeginTime(new Date()); //

            if (state.isClosed())
                verifyTask.setEndTime(entity.getVerifyEvalDate());
            else
                verifyTask.setEndTime(null);

            verifyTask.setRef(entity);

            Set<Principal> responsibles = new HashSet<Principal>(getDeclaredResponsibles(entity));
            verifyTask.setObservers(responsibles);
        }

        return result;
    }

}
