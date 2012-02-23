package com.bee32.plover.servlet.util;

import com.bee32.plover.arch.util.AssembledContext;
import com.bee32.plover.arch.util.BeanPartialContext;

public class HttpAssembledContext
        extends AssembledContext {

    public static final HttpPartialContext http = new HttpPartialContext();
    public static final BeanPartialContext bean = new BeanPartialContext(http);

}
