package com.bee32.plover.orm.util;

public interface ITypeAbbrAware {

    int ABBR_LEN = 10;
    TypeAbbr ABBR = new TypeAbbr(ABBR_LEN, //
            "com.bee32", //
            "user");

    String USAGE_ID = ITypeAbbrAware.class.getCanonicalName();

}
