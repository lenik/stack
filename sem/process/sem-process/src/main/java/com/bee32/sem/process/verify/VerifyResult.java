package com.bee32.sem.process.verify;

import com.bee32.icsf.principal.Principal;

public class VerifyResult {

    private final VerifyEvalState state;
    private final String message;

    public VerifyResult(VerifyEvalState state, String message) {
        this.state = state;
        this.message = message;
    }

    public VerifyEvalState getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public boolean isVerified() {
        return state == VerifyEvalState.VERIFIED;
    }

    @Override
    public String toString() {
        return message;
    }

    public static VerifyResult n_a(String message) {
        return new VerifyResult(VerifyEvalState.NOT_APPLICABLE, message);
    }

    public static VerifyResult pending(String message) {
        return new VerifyResult(VerifyEvalState.PENDING, message);
    }

    public static VerifyResult invalid(Principal user) {
        return new VerifyResult(VerifyEvalState.INVALID, "无效的审核人：" + user.getDisplayName());
    }

    public static VerifyResult rejected(Principal principal, String message) {
        message = "审核被 " + principal.getDisplayName() + " 拒绝：" + message;
        return new VerifyResult(VerifyEvalState.REJECTED, message);
    }

}
