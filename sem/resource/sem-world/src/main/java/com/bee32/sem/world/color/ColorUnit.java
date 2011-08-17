package com.bee32.sem.world.color;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class ColorUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Palette.class);
        add(PaletteColor.class);
    }

}
