package com.bee32.plover.orm.util;

import com.bee32.plover.arch.util.AssembledContext;
import com.bee32.plover.arch.util.BeanPartialContext;
import com.bee32.plover.servlet.util.HttpPartialContext;

public class DefaultDataAssembledContext
        extends AssembledContext {

    public static final HttpPartialContext http = HttpPartialContext.INSTANCE;
    public static final BeanPartialContext bean = new BeanPartialContext(http);
    public static final DataPartialContext data = new WiredDataPartialContext(bean);

}
