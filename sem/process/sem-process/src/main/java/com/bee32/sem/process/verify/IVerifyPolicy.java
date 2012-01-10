package com.bee32.sem.process.verify;

import java.util.Set;

import com.bee32.icsf.principal.Principal;

/**
 * 审核策略用于检验业务实体是否被审核，但不对业务的具体审核行为作出规定。
 *
 * 即策略用于“只读”、 “判断”的目的；而不是对业务执行”审核“动作、并形成审核结果的”写入“目的。
 */
public interface IVerifyPolicy {

    VerifyResult UNKNOWN = new VerifyResult(VerifyEvalState.UNKNOWN, null);
    VerifyResult VERIFIED = new VerifyResult(VerifyEvalState.VERIFIED, null);

    VerifyPolicyMetadata getMetadata();

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

    Set<?> getPredefinedStages();

    /**
     * 获取审核上下文的阶段。
     *
     * @return 非 <code>null</code> 的阶段键值。
     */
    Object getStage(IVerifyContext context);

    /**
     * 获取阶段上声明的责任人。
     *
     * @param stage
     *            阶段键值。
     * @return 非 <code>null </code>的显示声明的责任人集合。
     */
    Set<Principal> getStageResponsibles(Object stage);

    /**
     * 获取审核上下文在当前阶段的责任人。
     *
     * 相当于 {@link #getStageResponsibles(Object) getStageResponsibles}(
     * {@link #getStage(IVerifyContext) getStage()}).
     */
    Set<Principal> getResponsibles(IVerifyContext context);

    /**
     * 相当于 AND(getStageResponsibles(stage).contains(for each implied principal).
     *
     * 但可以高度优化。
     *
     * @return <code>true</code> 如果 principal 是指定阶段的责任人之一。
     */
    boolean isResponsible(Principal principal, Object stage);

}
