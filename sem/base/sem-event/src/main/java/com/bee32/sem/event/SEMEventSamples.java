package com.bee32.sem.event;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.event.entity.TaskPriority;
import com.bee32.sem.mail.SEMMailSamples;

@ImportSamples(SEMMailSamples.class)
public class SEMEventSamples
        extends EntitySamplesContribution {

    public static final TaskPriority HIGH = new TaskPriority("high", -100);
    public static final TaskPriority NORMAL = new TaskPriority("normal", 0);
    public static final TaskPriority LOW = new TaskPriority("low", 100);

    @Override
    protected void preamble() {
        addNormalSample(HIGH);
        addNormalSample(NORMAL);
        addNormalSample(LOW);
    }

}
