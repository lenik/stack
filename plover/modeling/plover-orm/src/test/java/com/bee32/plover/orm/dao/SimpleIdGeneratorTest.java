package com.bee32.plover.orm.dao;

import javax.free.IllegalUsageException;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.entity.Eauto;

public class SimpleIdGeneratorTest
        extends Assert {

    @Test(expected = IllegalUsageException.class)
    public void testGenerateSpec() {
        PloverConf conf = new PloverConf();
        SimpleIdGenerator.generate(conf);
    }

    @Test
    public void testGenerateInt() {
        IntCase a = new IntCase();
        int id = (Integer) SimpleIdGenerator.generate(a);
        assertEquals(1, id);
    }

    @Test
    public void testGenerateLong() {
        LongCase a = new LongCase();
        long id = (Long) SimpleIdGenerator.generate(a);
        assertEquals(1L, id);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGenerateDouble() {
        DoubleCase a = new DoubleCase();
        SimpleIdGenerator.generate(a);
    }

}

class IntCase
        extends Eauto<Integer> {

    private static final long serialVersionUID = 1L;

}

class LongCase
        extends Eauto<Long> {

    private static final long serialVersionUID = 1L;

}

class DoubleCase
        extends Eauto<Double> {

    private static final long serialVersionUID = 1L;

}