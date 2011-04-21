package com.bee32.sem.event;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.mail.SEMMailSamples;

@ImportSamples(SEMMailSamples.class)
public class SEMEventSamples
        extends EntitySamplesContribution {

    public static final EventPriority HIGH = new EventPriority("high", -100);
    public static final EventPriority NORMAL = new EventPriority("normal", 0);
    public static final EventPriority LOW = new EventPriority("low", 100);

    @Override
    protected void preamble() {
        addNormalSample(HIGH);
        addNormalSample(NORMAL);
        addNormalSample(LOW);
    }

}
