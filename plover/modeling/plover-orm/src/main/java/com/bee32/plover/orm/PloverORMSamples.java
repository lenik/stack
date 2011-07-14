package com.bee32.plover.orm;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.util.SamplesContribution;

public class PloverORMSamples
        extends SamplesContribution {

    public static PloverConf version = new PloverConf("version", "1.0");

    @Override
    protected void preamble() {
        addNormalSample(version);
    }

}
