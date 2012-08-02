package com.bee32.plover.arch.util.res;

import com.bee32.plover.arch.util.IPriority;

public interface INlsMessageProcessor
        extends IPriority {

    String processMessage(String message);

}
