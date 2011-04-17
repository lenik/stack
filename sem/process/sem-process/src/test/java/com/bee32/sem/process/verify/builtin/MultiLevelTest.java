package com.bee32.sem.process.verify.builtin;

import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.sem.process.SEMProcessUnit;
import com.bee32.sem.process.SEMVerifyPolicySamples;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelDao;

@Ignore
@Using(SEMProcessUnit.class)
public class MultiLevelTest
        extends WiredDaoTestCase {

    @Inject
    MultiLevelDao dao;

    @Test
    public void testLimitOrder() {
        Integer id = SEMVerifyPolicySamples.macLevel.getId();
        MultiLevel mlevel = dao.get(id);
        List<Level> ranges = mlevel.getLevels();
        long limit1 = ranges.get(0).getLimit();
        Level limit2 = ranges.get(1);
        assertEquals(1000, limit1);
        assertEquals(10000, limit2);
    }

}
