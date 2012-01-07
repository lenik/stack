package com.bee32.sem.process.verify.service;

import java.util.Set;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public interface IVerifyService {

    VerifyPolicy getPreferredVerifyPolicy(Class<? extends IVerifiable<?>> clazz);

    VerifyPolicyDto getPreferredVerifyPolicyDto(Class<? extends IVerifiable<?>> clazz);

    VerifyPolicy getVerifyPolicy(IVerifiable<?> obj);

    VerifyPolicyDto getVerifyPolicyDto(IVerifiable<?> obj);

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
    public boolean isVerified(IVerifiable<?> obj);

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
    void assertVerified(IVerifiable<?> context)
            throws VerifyException;

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
    VerifyResult verify(IVerifiable<?> context);

    /**
     * 重新计算审核状态并更新缓存，产生审核请求事件或更新事件状态，以及发送审核通知函给相关责任人。
     *
     * 您需要自己计算
     *
     * @param entity
     *            被计算并更新审核状态的实体，非 <code>null</code>。
     * @return 详细的审核结果。
     */
    VerifyResult verifyEntity(Entity<?> entity);

    Set<Principal> getResponsibles(IVerifiable<?> obj);

    boolean isResponsible(Principal principal, IVerifiable<?> obj);

}
