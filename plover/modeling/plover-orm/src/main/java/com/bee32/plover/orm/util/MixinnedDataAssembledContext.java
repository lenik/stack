package com.bee32.plover.orm.util;

public class MixinnedDataAssembledContext
        extends DefaultDataAssembledContext {

    public static final MixinnedBeanPartialContext bean = new MixinnedBeanPartialContext(http);
    public static final MixinnedWiredDataPartialContext data = new MixinnedWiredDataPartialContext(bean);

}
