package com.bee32.plover.orm.sample;

import com.bee32.plover.orm.sample.SuperSamplePackage.Standards;

public abstract class StandardSamples
        extends SamplePackage {

    @Override
    public int getLevel() {
        return LEVEL_STANDARD;
    }

    @Override
    protected SuperSamplePackage getSuperPackage() {
        return predefined(Standards.class);
    }

}
