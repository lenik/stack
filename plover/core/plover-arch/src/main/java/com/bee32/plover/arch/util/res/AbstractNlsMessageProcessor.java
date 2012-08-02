package com.bee32.plover.arch.util.res;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractNlsMessageProcessor
        implements INlsMessageProcessor {

    @Override
    public int getPriority() {
        return 0;
    }

}
