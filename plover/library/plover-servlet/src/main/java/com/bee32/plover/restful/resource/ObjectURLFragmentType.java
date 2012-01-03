package com.bee32.plover.restful.resource;

import java.util.Map;

public enum ObjectURLFragmentType {

    /** A short href string based on the module location. */
    baseHrefToModule(String.class),

    /** A String*String map contains the extra parameters. */
    extraParameters(Map.class),

    ;

    final Class<?> fragmentType;

    private ObjectURLFragmentType(Class<?> fragmentType) {
        if (fragmentType == null)
            throw new NullPointerException("fragmentType");
        this.fragmentType = fragmentType;
    }

    public Class<?> getFragmentType() {
        return fragmentType;
    }

}
