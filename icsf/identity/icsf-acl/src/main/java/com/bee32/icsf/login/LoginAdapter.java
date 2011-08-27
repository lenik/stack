package com.bee32.icsf.login;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.inject.ServiceTemplate;

@ComponentTemplate
@ServiceTemplate(prototype = true)
@Lazy
public abstract class LoginAdapter
        extends DataService
        implements ILoginListener {

    @Override
    public void logIn(LoginEvent event) {
    }

    @Override
    public void logOut(LoginEvent event) {
    }

    @Deprecated
    @Override
    public void refresh(LoginEvent event) {
    }

}
