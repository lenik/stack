package com.bee32.plover.orm.cache;

import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.site.scope.PerSite;

@PerSite
public abstract class DataCacheBean {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

}
