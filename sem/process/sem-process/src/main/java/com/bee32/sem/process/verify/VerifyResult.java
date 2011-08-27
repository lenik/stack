package com.bee32.sem.process.verify;

import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;

public class VerifyResult {

    private final VerifyState state;
    private final String message;

    public VerifyResult(VerifyState state, String message) {
        this.state = state;
        this.message = message;
    }

    public VerifyState getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public boolean isVerified() {
        return state == VerifyState.VERIFIED;
    }

    @Override
    public String toString() {
        return message;
    }

    public static VerifyResult n_a(String message) {
        return new VerifyResult(VerifyState.NOT_APPLICABLE, message);
    }

    public static VerifyResult pending(String message) {
        return new VerifyResult(VerifyState.PENDING, message);
    }

    public static VerifyResult invalid(User user) {
        return new VerifyResult(VerifyState.INVALID, "无效的审核人：" + user.getDisplayName());
    }

    public static VerifyResult rejected(Principal principal, String message) {
        message = "审核被 " + principal.getDisplayName() + " 拒绝：" + message;
        return new VerifyResult(VerifyState.REJECTED, message);
    }

}
