package com.bee32.sem.calendar;

import com.bee32.plover.orm.ext.util.CEnum;
import com.bee32.plover.servlet.context.ILocationConstants;
import com.bee32.plover.servlet.context.Location;

public enum DiaryVisibility
        implements CEnum, ILocationConstants {

    PRIVATE('-', "yellowlock.png"),

    COMMIT('<', "triangle"),

    PROTECTED_SELECTION('#', "greenlock.png"),

    PROTECTED('*', "greenlock.png"),

    SELECTION('S', "filter.png"),

    PUBLIC('+', "public.png"),

    ;

    final char ch;
    final Location icon;

    static class _ {
        static final CharMap<DiaryVisibility> charMap = new CharMap<DiaryVisibility>();
    }

    private DiaryVisibility(char ch, String icon) {
        this.ch = ch;
        this.icon = ICON.join(icon);
        _.charMap.put(ch, this);
    }

    public Location getIcon() {
        return icon;
    }

    @Override
    public char toChar() {
        return ch;
    }

    public static DiaryVisibility fromChar(char ch) {
        return _.charMap.get(ch);
    }

}
