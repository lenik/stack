package com.bee32.sem.process.verify.result;

import com.bee32.icsf.principal.User;

public class UnauthorizedResult
        extends ErrorResult {

    public UnauthorizedResult(User user) {
        super("无效的审核人：" + user);
    }

}
