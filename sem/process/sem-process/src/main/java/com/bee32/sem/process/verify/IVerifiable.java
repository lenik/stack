package com.bee32.sem.process.verify;

/**
 * 接受审核的业务实体。
 *
 * 如：
 * <ul>
 * <li>采购请求，再采购尚未发生之前，由上级部门审核允许。
 * <li>入库请求，仓库管理人员审核入库单与交接物品是否一致。
 * </ul>
 */
public interface IVerifiable {

    /**
     * 该业务采用的审核策略。
     */
    IVerifyPolicy getVerifyPolicy();

    /**
     * 查询审核数据。
     *
     * 如果 {@link IVerifiable#getVerifyData()} 返回 <code>null</code>
     * 并不表示该业务不被审核，如果该业务隐含默认的审核规则，比如“采购的金额未超过大宗采购的最小值”，则该业务即被认为默认审核了。
     * <p>
     * 同理，如果 {@link IVerifiable#getVerifyState()} 返回非 <code>null</code>
     * ，但审核数据中填写的却是类似“拒绝”等信息时，该业务实际未被审核（并且是终止态）。
     * <p>
     * 审核数据的具体含义只有通过审核策略的解释才能确定。
     *
     * @return 审核状态数据。
     */
    VerifyState getVerifyState();

}
