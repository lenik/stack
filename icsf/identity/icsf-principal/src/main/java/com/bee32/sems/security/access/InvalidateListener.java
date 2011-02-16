package com.bee32.sems.security.access;

import java.util.EventListener;

public interface InvalidateListener
        extends EventListener {

    void invalidate(InvalidateEvent event);

}
