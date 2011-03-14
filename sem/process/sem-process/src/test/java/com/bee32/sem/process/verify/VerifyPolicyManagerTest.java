package com.bee32.sem.process.verify;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.IContextLimit;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.PassToNext;

public class VerifyPolicyManagerTest
        extends Assert {

    @Test
    public void testListAll() {
        Collection<Class<? extends IVerifyPolicy<?, ?>>> all //
        = VerifyPolicyManager.getAvailableVerifyPoliicyClasses();

        assertTrue(all.contains(AllowList.class));
        assertTrue(all.contains(MultiLevel.class));
        assertTrue(all.contains(PassToNext.class));
    }

    @Test
    public void testMatch() {
        Collection<Class<? extends IVerifyPolicy<?, ?>>> all //
        = VerifyPolicyManager.getAvailableVerifyPoliicyClasses(SimpleBean.class);

        assertTrue(all.contains(AllowList.class));
        assertTrue(all.contains(MultiLevel.class));
        assertFalse(all.contains(PassToNext.class));
    }

    static class SimpleBean
            implements IContextLimit {

        @Override
        public long getContextLimit() {
            return 1000;
        }

    }

}
