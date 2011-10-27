package com.bee32.plover.site;

import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class EnumUtilTest {

    @Test
    public void testValues() {
        RetentionPolicy[] retentionPolicies = EnumUtil.values(RetentionPolicy.class);
        List<RetentionPolicy> policies = Arrays.asList(retentionPolicies);
        policies.contains(RetentionPolicy.SOURCE);
        policies.contains(RetentionPolicy.CLASS);
        policies.contains(RetentionPolicy.RUNTIME);
    }

}
