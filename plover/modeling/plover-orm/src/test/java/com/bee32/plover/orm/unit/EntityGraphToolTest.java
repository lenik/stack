package com.bee32.plover.orm.unit;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.xgraph.EntityGraphTool;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(PloverORMUnit.class)
public class EntityGraphToolTest
        extends WiredDaoTestCase {

    @Inject
    EntityGraphTool graphTool;

    @Test
    public void testBuildGraphs() {

    }

}
