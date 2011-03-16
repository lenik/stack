package com.bee32.sem.process.verify.result;

import com.bee32.icsf.principal.Principal;

public class RejectedResult
        extends ErrorResult {

    public RejectedResult(Principal principal, String message) {
        super(String.format("审核被 %s 拒绝：%s", principal, message));
    }

}
