package com.bee32.plover.servlet.peripheral;

import com.bee32.plover.inject.ActivatorServiceBooter;

public class ActivatorServiceBooterScl
        extends AbstractScl {

    public ActivatorServiceBooterScl() {
        ActivatorServiceBooter.bootup();
    }

    @Override
    public int getPriority() {
        return -10000;
    }

}
