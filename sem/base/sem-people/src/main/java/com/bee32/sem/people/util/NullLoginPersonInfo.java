package com.bee32.sem.people.util;

import com.bee32.sem.people.entity.Person;

class NullLoginPersonInfo
        extends LoginPersonInfo {

    private static final long serialVersionUID = 1L;

    public NullLoginPersonInfo() {
        super(null);
    }

    @Override
    public Person getPersonOpt() {
        return null;
    }

    public static final NullLoginPersonInfo INSTANCE = new NullLoginPersonInfo();

}
