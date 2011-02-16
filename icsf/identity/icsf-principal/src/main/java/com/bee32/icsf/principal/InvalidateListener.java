package com.bee32.icsf.principal;

import java.util.EventListener;

public interface InvalidateListener
        extends EventListener {

    void invalidate(InvalidateEvent event);

}
