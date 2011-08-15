package com.bee32.sem.world.monetary;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.test.TestSupport;
import com.bee32.sem.misc.i18n.ICurrencyAware;

public class MCValueTest
        extends TestSupport
        implements ICurrencyAware {

    @Test
    public void testEquality() {
        MCValue v1 = new MCValue(JPY, 12);
        MCValue v2 = new MCValue(JPY, 12.0);
        assertEquals(v1, v2);
    }

    @Test
    public void testToNative()
            throws FxrQueryException {
        Date date = parseDate("2011-7-25");

        MCValue v1 = new MCValue(USD, 1);
        MCValue nat = v1.toNative(date);
        assertEqualsX(6.442655f, nat.getValue().floatValue());

        MCValue v2 = v1.multiply(2);
        MCValue nat2 = v2.toNative(date);
        assertEqualsX(6.442655f * 2, nat2.getValue().floatValue());
    }

    public static void main(String[] args) {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
        MCValue mcv = new MCValue();
        System.out.println(mcv.getCurrency());
    }

}
