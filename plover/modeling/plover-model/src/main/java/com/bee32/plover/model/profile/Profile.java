package com.bee32.plover.model.profile;

import com.bee32.plover.model.qualifier.DerivedQualifier;

public class Profile
        extends DerivedQualifier<Profile> {

    private static final long serialVersionUID = 1L;

    public Profile(String name) {
        this(name, null);
    }

    public Profile(String name, Profile kindOf) {
        super(Profile.class, name, kindOf);
    }

    @Override
    public int compareSpecific(Profile o) {
        return getName().compareTo(o.getName());
    }

}
