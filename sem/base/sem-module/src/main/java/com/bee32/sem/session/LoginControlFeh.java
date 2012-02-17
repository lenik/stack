package com.bee32.sem.session;

import com.bee32.icsf.login.LoginControl;
import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.arch.exception.ForException;
import com.bee32.plover.faces.FaceletExceptionContext;
import com.bee32.plover.faces.FaceletsExceptionHandler;

/**
 * 将登录失败翻译为重定向。
 */
@ForException(value = LoginControl.class, fullStackSearch = true)
public class LoginControlFeh
        extends FaceletsExceptionHandler {

    @Override
    public int getPriority() {
        return -1000;
    }

    @Override
    public ExceptionHandleResult handle(Throwable exception, FaceletExceptionContext context) {
        return redirect("/3/7/1/login.jsf");
    }

}
