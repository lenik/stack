package com.bee32.sem.uber;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.unit.EntityGraph;
import com.bee32.plover.orm.unit.EntityGraphTool;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.ox1.principal.User;

@Using(SEMUberUnit.class)
public class SEMUberGraphTest
        extends WiredDaoTestCase {

    @Inject
    EntityGraphTool graphTool;

    @Test
    public void testBuildGraphs() {
        EntityGraph userGraph = graphTool.getEntityGraph(User.class);
        System.out.println(userGraph);
    }

}
