package com.bee32.sem.session;

import com.bee32.icsf.login.LoginException;
import com.bee32.plover.web.faces.AnnotatedFeh;
import com.bee32.plover.web.faces.ExceptionHandleResult;
import com.bee32.plover.web.faces.FaceletExceptionContext;
import com.bee32.plover.web.faces.ForException;

/**
 * 将登录失败翻译为重定向。
 */
@ForException(value = LoginException.class, fullStackSearch = true)
public class LoginFeh
        extends AnnotatedFeh {

    @Override
    public int getPriority() {
        return -1000;
    }

    @Override
    public ExceptionHandleResult handle(FaceletExceptionContext context, Throwable exception) {
        return redirect("/3/7/1/login.jsf");
    }

}
