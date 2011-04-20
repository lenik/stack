package com.bee32.sem.event;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.mail.SEMMailSamples;

@ImportSamples(SEMMailSamples.class)
public class SEMEventSamples
        extends EntitySamplesContribution {

    @Override
    protected void preamble() {
    }

}
