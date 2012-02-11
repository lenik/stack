package com.bee32.plover.faces.utils;

import com.bee32.plover.arch.util.AssembledContext;
import com.bee32.plover.arch.util.BeanPartialContext;

public class FacesAssembledContext
        extends AssembledContext {

    public static final FacesPartialContext view = FacesPartialContext.INSTANCE;
    public static final BeanPartialContext bean = new BeanPartialContext(view);

}
