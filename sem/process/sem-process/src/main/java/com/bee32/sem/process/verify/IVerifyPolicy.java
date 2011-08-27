package com.bee32.sem.process.verify;

import java.util.Set;

import com.bee32.plover.ox1.principal.Principal;

/**
 * 审核策略用于检验业务实体是否被审核，但不对业务的具体审核行为作出规定。
 *
 * 即策略用于“只读”、 “判断”的目的；而不是对业务执行”审核“动作、并形成审核结果的”写入“目的。
 */
public interface IVerifyPolicy {

    /**
     * 获取必要的上下文类型。
     */
    Class<? extends IVerifyContext> getRequiredContext();

    VerifyResult verify(IVerifyContext context);

    /**
     * 检验业务实体是否已被审核。 如果未审核或审核失败，抛出 {@link VerifyException} 异常。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @throws VerifyException
     *             如果未被审核
     */
    void assertVerified(IVerifyContext context)
            throws VerifyException;

    /**
     * 判断业务实体是否已被审核，如果已审核返回 <code>true</code>。
     *
     * 等效于：
     *
     * <pre>
     * try {
     *     verify(verifyObject);
     *     return true;
     * } catch (BadVerifyDataException e) {
     *     throw e;
     * } catch (VerifyException e) {
     *     return false;
     * }
     * </pre>
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     */
    boolean isVerified(IVerifyContext context);

    /**
     * 获取显示声明的责任人。
     *
     * @param context
     *            上下文，<code>null</code> 适用于无具体的上下文。
     * @return 非 <code>null </code>的显示声明的责任人集合。
     */
    Set<Principal> getDeclaredResponsibles(IVerifyContext context);

}
