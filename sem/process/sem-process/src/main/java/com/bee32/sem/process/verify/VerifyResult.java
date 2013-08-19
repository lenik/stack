package com.bee32.sem.process.verify;

import com.bee32.icsf.principal.Principal;

/**
 * 审核结果
 *
 * <p lang="en">
 * Verify Result
 */
public class VerifyResult {

    private final VerifyEvalState state;
    private final String message;

    public VerifyResult(VerifyEvalState state, String message) {
        this.state = state;
        this.message = message;
    }

    /**
     * 状态
     *
     * <p lang="en">
     */
    public VerifyEvalState getState() {
        return state;
    }

    /**
     * 消息
     *
     * <p lang="en">
     */
    public String getMessage() {
        return message;
    }

    /**
     * 已审核
     *
     * <p lang="en">
     */
    public boolean isVerified() {
        return state == VerifyEvalState.VERIFIED;
    }

    @Override
    public String toString() {
        return message;
    }

    /**
     * 不可用
     *
     * <p lang="en">
     */
    public static VerifyResult n_a(String message) {
        return new VerifyResult(VerifyEvalState.NOT_APPLICABLE, message);
    }

    /**
     * 已挂起
     *
     * <p lang="en">
     */
    public static VerifyResult pending(String message) {
        return new VerifyResult(VerifyEvalState.PENDING, message);
    }

    /**
     * 非法的
     *
     * <p lang="en">
     */
    public static VerifyResult invalid(Principal user) {
        return new VerifyResult(VerifyEvalState.INVALID, "无效的审核人：" + user.getDisplayName());
    }

    /**
     * 已拒绝
     *
     * <p lang="en">
     */
    public static VerifyResult rejected(Principal principal, String message) {
        message = "审核被 " + principal.getDisplayName() + " 拒绝：" + message;
        return new VerifyResult(VerifyEvalState.REJECTED, message);
    }

}
