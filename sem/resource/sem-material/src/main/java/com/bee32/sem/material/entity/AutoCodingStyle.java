package com.bee32.sem.material.entity;

import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;

public class AutoCodingStyle
        extends EnumAlt<Character, AutoCodingStyle> {

    private static final long serialVersionUID = 1L;

    public AutoCodingStyle(Character value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, AutoCodingStyle> getNameMap() {
        return null;
    }

    @Override
    protected Map<Character, AutoCodingStyle> getValueMap() {
        return null;
    }

    public static final AutoCodingStyle NONE = new AutoCodingStyle('N', "none");
    public static final AutoCodingStyle GUID = new AutoCodingStyle('G', "guid");

}
