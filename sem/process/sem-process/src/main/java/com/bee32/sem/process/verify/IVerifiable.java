package com.bee32.sem.process.verify;

/**
 * 接受审核的业务实体。
 *
 * 如：
 * <ul>
 * <li>采购请求，再采购尚未发生之前，由上级部门审核允许。
 * <li>入库请求，仓库管理人员审核入库单与交接物品是否一致。
 * </ul>
 *
 * @param C
 *            特定类型的审核上下文。
 */
public interface IVerifiable<C extends IVerifyContext> {

    /**
     * 该业务采用的审核策略。
     */
    IVerifyPolicy<C> getVerifyPolicy();

    /**
     * 获取该业务的审核上下文。
     */
    C getVerifyContext();

}
