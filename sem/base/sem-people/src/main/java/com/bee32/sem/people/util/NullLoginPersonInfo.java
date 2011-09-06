package com.bee32.sem.people.util;

import com.bee32.sem.people.dto.PersonDto;

class NullLoginPersonInfo
        extends LoginPersonInfo {

    private static final long serialVersionUID = 1L;

    public NullLoginPersonInfo() {
        super(null);
    }

    @Override
    public PersonDto getPersonOpt() {
        return null;
    }

    public static final NullLoginPersonInfo INSTANCE = new NullLoginPersonInfo();

}
