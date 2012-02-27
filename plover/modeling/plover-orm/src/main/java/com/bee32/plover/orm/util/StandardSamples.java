package com.bee32.plover.orm.util;

public class StandardSamples
        extends NormalSamples {

    public StandardSamples() {
        super(DiamondPackage.STANDARD);
    }

    @Override
    public int getLevel() {
        return LEVEL_STANDARD;
    }

}
