package com.bee32.sem.process.verify;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.process.verify.builtin.AllowListPolicy;
import com.bee32.sem.process.verify.builtin.IMultiLevelContext;
import com.bee32.sem.process.verify.builtin.MultiLevelPolicy;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.util.AllowedBySupport;

public class VerifyPolicyManagerTest
        extends Assert {

    @Test
    public void testListAll() {
        Collection<Class<? extends VerifyPolicy>> all //
        = VerifyPolicyManager.list();

        assertTrue(all.contains(AllowListPolicy.class));
        assertTrue(all.contains(MultiLevelPolicy.class));
        assertTrue(all.contains(PassToNextPolicy.class));
    }

    @Test
    public void testMatch() {
        Collection<Class<? extends VerifyPolicy>> all //
        = VerifyPolicyManager.forBean(SimpleBean.class);

        assertTrue(all.contains(AllowListPolicy.class));
        assertTrue(all.contains(MultiLevelPolicy.class));
        assertFalse(all.contains(PassToNextPolicy.class));
    }

    static class SimpleBean
            extends AllowedBySupport<Long, IMultiLevelContext>
            implements IMultiLevelContext {

        private static final long serialVersionUID = 1L;

        long longValue;

        public SimpleBean() {
            longValue = 1000;
        }

        @Override
        public String getValueDescription() {
            return "金额";
        }

        @Override
        public long getLongValue() {
            return longValue;
        }

    }

}
