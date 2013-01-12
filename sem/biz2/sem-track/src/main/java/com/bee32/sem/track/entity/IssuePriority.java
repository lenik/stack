package com.bee32.sem.track.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class IssuePriority
        extends EnumAlt<Integer, IssuePriority> {

    private static final long serialVersionUID = 1L;

    public IssuePriority(Integer value, String name, String description) {
        super(value, name);
    }

    public static IssuePriority forName(String name) {
        return forName(IssuePriority.class, name);
    }

    public static Collection<IssuePriority> values() {
        return values(IssuePriority.class);
    }

    public static IssuePriority forValue(Integer value) {
        return forValue(IssuePriority.class, value);
    }

    public static final IssuePriority important = new IssuePriority(1, "important", "重要");
    public static final IssuePriority urgent = new IssuePriority(2, "urgent", "紧急");
    public static final IssuePriority purgent = new IssuePriority(3, "purgent", "加急");
    public static final IssuePriority normal = new IssuePriority(4, "normal", "普通");
    public static final IssuePriority minor = new IssuePriority(5, "minor", "次要");

}
