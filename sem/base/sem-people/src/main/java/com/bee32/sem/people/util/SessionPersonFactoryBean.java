package com.bee32.sem.people.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;

@Component("sessionPerson")
@PerSession
public class SessionPersonFactoryBean
        implements FactoryBean<SessionPerson> {

    @Override
    public SessionPerson getObject()
            throws Exception {
        return SessionPerson.getInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return SessionPerson.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
