package com.bee32.sem.calendar;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;

public class DiaryVisibility
        extends EnumAlt<Character, DiaryVisibility>
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    static final Map<String, DiaryVisibility> nameMap = new HashMap<String, DiaryVisibility>();
    static final Map<Character, DiaryVisibility> valueMap = new HashMap<Character, DiaryVisibility>();

    final Location icon;

    private DiaryVisibility(char value, String name, String icon) {
        super(value, name);
        this.icon = ICON.join(icon);
    }

    public Location getIcon() {
        return icon;
    }

    @Override
    protected Map<String, DiaryVisibility> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, DiaryVisibility> getValueMap() {
        return valueMap;
    }

    public static DiaryVisibility forName(String name) {
        DiaryVisibility instance = nameMap.get(name);
        if (instance != null)
            return instance;
        throw new IllegalUsageException(//
                "Invalid DiaryVisibility name: " + name);
    }

    public static DiaryVisibility valueOf(char value) {
        DiaryVisibility instance = valueMap.get(value);
        if (instance != null)
            return instance;
        throw new IllegalUsageException(String.format(//
                "Invalid DiaryVisibility value: 0x%x" + value));
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
