package com.bee32.sem.world.color;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

@Embeddable
public class ColorSystem
        extends EnumAlt<String, ColorSystem> {

    private static final long serialVersionUID = 1L;

    static final Map<String, ColorSystem> nameMap = new HashMap<String, ColorSystem>();
    static final Map<String, ColorSystem> valueMap = new HashMap<String, ColorSystem>();

    public ColorSystem(String value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, ColorSystem> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, ColorSystem> getValueMap() {
        return valueMap;
    }

    public static Collection<ColorSystem> values() {
        Collection<ColorSystem> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static ColorSystem forName(String altName) {
        ColorSystem colorSystem = nameMap.get(altName);
        if (colorSystem == null)
            throw new NoSuchEnumException(ColorSystem.class, altName);
        return colorSystem;
    }

    public static ColorSystem valueOf(String value) {
        if (value == null)
            return null;

        ColorSystem colorSystem = valueMap.get(value);
        if (colorSystem == null)
            throw new NoSuchEnumException(ColorSystem.class, value);

        return colorSystem;
    }

    public static ColorSystem TRUE_COLOR = new ColorSystem("TC", "trueColor");
    public static ColorSystem P = new ColorSystem("TC", "trueColor");

}
