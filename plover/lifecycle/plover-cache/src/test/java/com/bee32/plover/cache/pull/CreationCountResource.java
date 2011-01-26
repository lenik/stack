package com.bee32.plover.cache.pull;

import java.util.List;

import com.bee32.plover.cache.auto.CreateRuleResource;
import com.bee32.plover.cache.auto.IMakeSchema;

public class CreationCountResource
        extends CreateRuleResource<String> {

    private int createCount = 0;

    public CreationCountResource(IMakeSchema cacheSchema) {
        super(cacheSchema);
    }

    public int getCreateCount() {
        return createCount;
    }

    @Override
    protected String create(List<?> resolvedDependencies)
            throws Exception {
        createCount++;
        return String.valueOf(createCount);
    }

}
