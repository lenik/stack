package com.bee32.sem.process.verify;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.builtin.AllowListPolicy;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.MultiLevelPolicy;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.util.SingleVerifierWithNumberSupport;

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

    @Entity
    @SequenceGenerator(name = "idgen", sequenceName = "vpm_simple_bean_seq", allocationSize = 1)
    static class SimpleBean
            extends UIEntityAuto<Long>
            implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

        private static final long serialVersionUID = 1L;
        private SingleVerifierWithNumberSupport context = new SingleVerifierWithNumberSupport(this);

        long longValue;

        public SimpleBean() {
            longValue = 1000;
        }

        @Override
        public String getNumberDescription() {
            return "金额";
        }

        @Override
        public Number getJudgeNumber() {
            return longValue;
        }

        @Override
        public ISingleVerifierWithNumber getVerifyContext() {
            return context;
        }

    }

}
