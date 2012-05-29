package com.bee32.sem.calendar;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;

public class DiaryVisibility
        extends EnumAlt<Character, DiaryVisibility>
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    final Location icon;

    private DiaryVisibility(char value, String name, String icon) {
        super(value, name);
        this.icon = ICON.join(icon);
    }

    @Override
    public Location getIcon() {
        return icon;
    }

    public static DiaryVisibility forName(String name) {
        return forName(DiaryVisibility.class, name);
    }

    public static Collection<DiaryVisibility> values() {
        return values(DiaryVisibility.class);
    }

    public static DiaryVisibility forValue(Character value) {
        return forValue(DiaryVisibility.class, value);
    }

    public static DiaryVisibility forValue(char value) {
        return forValue(new Character(value));
    }

    public static final DiaryVisibility PRIVATE //
    /*          */= new DiaryVisibility('-', "private", "etool16/yellowlock.png");

    public static final DiaryVisibility COMMIT//
    /*          */= new DiaryVisibility('<', "commit", "etool16/triangle.png");

    public static final DiaryVisibility PROTECTED_SELECTION //
    /*          */= new DiaryVisibility('#', "protectedSelection", "etool16/greenlock.png");

    public static final DiaryVisibility PROTECTED //
    /*          */= new DiaryVisibility('*', "protected", "etool16/greenlock.png");

    public static final DiaryVisibility SELECTION //
    /*          */= new DiaryVisibility('S', "selection", "etool16/filter.png");

    public static final DiaryVisibility PUBLIC //
    /*          */= new DiaryVisibility('+', "public", "etool16/public.png");

}