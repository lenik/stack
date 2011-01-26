package com.bee32.plover.cache.pull;

import org.junit.Assert;

import com.bee32.plover.cache.auto.IMakeSchema;
import com.bee32.plover.cache.auto.SimpleMakeSchema;

public class AbstractResourceTest
        extends Assert {

    protected IMakeSchema schema;

    public AbstractResourceTest() {
        this.schema = newSchema();
    }

    protected IMakeSchema newSchema() {
        return new SimpleMakeSchema();
    }

}
