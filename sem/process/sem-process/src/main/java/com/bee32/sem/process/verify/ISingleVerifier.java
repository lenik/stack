package com.bee32.sem.process.verify;

import java.util.Date;

import com.bee32.plover.ox1.principal.User;

/**
 * Used by single man verify policies.
 */
public interface ISingleVerifier
        extends IVerifyContext {

    /**
     * 审核用户。
     *
     * @return 返回 <code>null</code> 表示尚未审核。
     */
    User getVerifier1();

    /**
     * 审核的时间。
     *
     * @return 如果尚未审核则返回 <code>null</code>。
     */
    Date getVerifiedDate1();

    /**
     * 返回 <code>true</code> 表示允许，<code>false</code> 表示被拒绝。如果尚未审核则返回 <code>null</code>。
     */
    Boolean isAccepted1();

    /**
     * 获取被拒绝的原因。
     *
     * @return 被拒绝的原因，如果没有原因或审核被允许则返回 <code>null</code>。
     */
    String getRejectedReason1();

}
