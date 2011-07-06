package com.bee32.sem.session;

import com.bee32.icsf.login.LoginException;
import com.bee32.plover.web.faces.AnnotatedFaceletExceptionHandler;
import com.bee32.plover.web.faces.FaceletExceptionContext;
import com.bee32.plover.web.faces.ForException;

@ForException(LoginException.class)
public class LoginExceptionHandler
        extends AnnotatedFaceletExceptionHandler {

    @Override
    public String handle(FaceletExceptionContext context, Throwable exception) {
        return "/login.jsf";
    }

}
