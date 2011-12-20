package com.bee32.sem.process.verify.builtin;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.sem.process.SEMProcessUnit;
import com.bee32.sem.process.SEMVerifyPolicySamples;

@Ignore
@Using(SEMProcessUnit.class)
public class SingleVerifierRankedPolicyTest
        extends WiredDaoTestCase {

    @Test
    public void testLimitOrder() {
        Integer id = SEMVerifyPolicySamples.macLevel.getId();
        SingleVerifierRankedPolicy mlevel = asFor(SingleVerifierRankedPolicy.class).get(id);
        List<SingleVerifierLevel> ranges = mlevel.getLevels();
        long limit1 = ranges.get(0).getLimit();
        SingleVerifierLevel limit2 = ranges.get(1);
        assertEquals(1000, limit1);
        assertEquals(10000, limit2);
    }

}
