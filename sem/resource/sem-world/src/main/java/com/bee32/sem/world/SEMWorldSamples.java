package com.bee32.sem.world;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.world.monetary.MonetarySamples;
import com.bee32.sem.world.thing.ThingSamples;

@ImportSamples({ ThingSamples.class, MonetarySamples.class })
public class SEMWorldSamples
        extends SampleContribution {

}
