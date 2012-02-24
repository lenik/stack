package com.bee32.sem.process.verify.builtin;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.process.SEMVerifyPolicySamples;

public class SingleVerifierRankedPolicyTest
        extends Assert {

    @Test
    public void testLimitOrder() {
        SEMVerifyPolicySamples policies = new SEMVerifyPolicySamples();
        SingleVerifierRankedPolicy policy = policies.macLevel;
        List<SingleVerifierLevel> levels = policy.getLevels();
        long limit1 = levels.get(0).getLimit();
        SingleVerifierLevel level2 = levels.get(1);
        assertEquals(1000, limit1);
        assertEquals(10000, level2.getLimit());
    }

}
