package com.bee32.icsf.login;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;

@ServiceTemplate
public abstract class AbstractLoginListener
        implements ILoginListener {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

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
