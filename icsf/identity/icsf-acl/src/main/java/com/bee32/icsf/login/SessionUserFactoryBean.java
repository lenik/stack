package com.bee32.icsf.login;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component("sessionUser")
@PerSession
public class SessionUserFactoryBean
        implements FactoryBean<SessionUser> {

    @Override
    public SessionUser getObject()
            throws Exception {
        return SessionUser.getInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return SessionUser.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
