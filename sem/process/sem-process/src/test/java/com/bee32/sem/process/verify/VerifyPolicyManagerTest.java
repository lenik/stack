package com.bee32.sem.process.verify;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.IMultiLevelContext;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.PassToNext;
import com.bee32.sem.process.verify.util.AllowedBySupport;

public class VerifyPolicyManagerTest
        extends Assert {

    @Test
    public void testListAll() {
        Collection<Class<? extends VerifyPolicy>> all //
        = VerifyPolicyManager.list();

        assertTrue(all.contains(AllowList.class));
        assertTrue(all.contains(MultiLevel.class));
        assertTrue(all.contains(PassToNext.class));
    }

    @Test
    public void testMatch() {
        Collection<Class<? extends VerifyPolicy>> all //
        = VerifyPolicyManager.forBean(SimpleBean.class);

        assertTrue(all.contains(AllowList.class));
        assertTrue(all.contains(MultiLevel.class));
        assertFalse(all.contains(PassToNext.class));
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
