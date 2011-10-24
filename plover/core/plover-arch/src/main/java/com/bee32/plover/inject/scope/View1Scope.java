package com.bee32.plover.inject.scope;

import org.springframework.beans.factory.ObjectFactory;

public class View1Scope
        extends AbstractScope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return null;
    }

    @Override
    public Object remove(String name) {
        return null;
    }

}
