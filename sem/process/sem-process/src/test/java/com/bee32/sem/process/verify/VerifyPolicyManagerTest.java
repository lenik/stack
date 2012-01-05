package com.bee32.sem.process.verify;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.util.dto.BeanPropertyAccessor;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

public class VerifyPolicyManagerTest
        extends Assert {

    @Test
    public void testListAll() {
        Collection<Class<? extends IVerifyPolicy>> all = VerifyPolicyManager.list();
        assertTrue(all.contains(SingleVerifierPolicy.class));
        assertTrue(all.contains(SingleVerifierRankedPolicy.class));
        assertTrue(all.contains(PassToNextPolicy.class));
    }

    @Test
    public void testMatch() {
        Collection<Class<? extends IVerifyPolicy>> all = VerifyPolicyManager.forBean(SimpleBean.class);
        assertTrue(all.contains(SingleVerifierPolicy.class));
        assertTrue(all.contains(SingleVerifierRankedPolicy.class));
        assertFalse(all.contains(PassToNextPolicy.class));
    }

    @Entity
    @SequenceGenerator(name = "idgen", sequenceName = "vpm_simple_bean_seq", allocationSize = 1)
    static class SimpleBean
            extends UIEntityAuto<Long>
            implements IVerifiable<ISingleVerifierWithNumber> {

        private static final long serialVersionUID = 1L;

        static final IPropertyAccessor<Long> valueProperty = BeanPropertyAccessor.access(SimpleBean.class, "value");
        private SingleVerifierWithNumberSupport context = new SingleVerifierWithNumberSupport(this, valueProperty, "金额");

        long longValue;

        public SimpleBean() {
            longValue = 1000;
        }

        public long getValue() {
            return longValue;
        }

        @Override
        public ISingleVerifierWithNumber getVerifyContext() {
            return context;
        }

    }

}
