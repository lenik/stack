package com.bee32.plover.orm.util;

import com.bee32.plover.arch.util.AssembledContext;
import com.bee32.plover.arch.util.BeanPartialContext;
import com.bee32.plover.servlet.util.BootstrapApplicationContextPartialContext;

public class BootstrapDataAssembledContext
        extends AssembledContext {

    public static final BeanPartialContext bean = new BeanPartialContext(
            BootstrapApplicationContextPartialContext.INSTANCE);
    public static final DataPartialContext data = new WiredDataPartialContext(bean);

}
