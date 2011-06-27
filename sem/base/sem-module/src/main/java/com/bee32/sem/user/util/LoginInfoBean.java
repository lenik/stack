package com.bee32.sem.user.util;

import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadServletContext;

@Component
@Scope("session")
@Named("loginInfo")
public class LoginInfoBean
        extends LoginInfo {

    HttpSession session;

    @Override
    protected HttpSession getSession() {
        // return session;
        return ThreadServletContext.getSession();
    }

    // @Inject
    @Override
    protected void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    protected HttpSession requireSession() {
        return ThreadServletContext.requireSession();
    }

}