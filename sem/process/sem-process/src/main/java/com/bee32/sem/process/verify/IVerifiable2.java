package com.bee32.sem.process.verify;

public interface IVerifiable2<C extends IVerifyContext>
        extends IVerifiable<C> {

    /**
     * 该业务采用的审核策略。
     *
     * 如果采用类型默认的审核策略，即使用：
     *
     * <pre>
     * return VerifyPolicyManager.forEntityType(getClass());
     * </pre>
     *
     * 的话，那么不要忘了在 Entity 的对应方法上添加 &64;Transient 标注。
     *
     * @return Non-<code>null</code> verify policy implementation.
     */
    IVerifyPolicy<C> getVerifyPolicy();

    /**
     * The same as
     *
     * <pre>
     * getVerifyPolicy().verify(getVerifyContext());
     * </pre>
     */
    void verify()
            throws VerifyException;

    /**
     * <pre>
     * getVerifyPolicy().isVerified(getVerifyContext());
     * </pre>
     */
    boolean isVerified();

}
