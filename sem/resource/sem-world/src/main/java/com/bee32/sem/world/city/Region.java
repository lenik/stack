package com.bee32.sem.world.city;

import com.bee32.plover.arch.util.EnumAlt;

public abstract class Region<$ extends Region<$>>
        extends EnumAlt<String, $> {

    private static final long serialVersionUID = 1L;

    public Region(String value, String name) {
        super(value, name);
    }

}
