package com.bee32.icsf.util;

import java.util.EventListener;

public interface InvalidateListener
        extends EventListener {

    void invalidate(InvalidateEvent event);

}
