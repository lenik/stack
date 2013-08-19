package com.bee32.sem.world.color;

import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * （通用）色彩表示数据单元
 *
 * <p lang="en">
 * (General) Color Representation Unit
 */
public class ColorUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Palette.class);
        add(PaletteEntry.class);
    }

}
