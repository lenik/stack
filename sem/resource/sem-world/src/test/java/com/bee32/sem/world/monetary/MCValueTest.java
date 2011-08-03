package com.bee32.sem.world.monetary;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.misc.i18n.ICurrencyAware;

public class MCValueTest
        extends Assert
        implements ICurrencyAware {

    @Test
    public void testEquality() {
        MCValue v1 = new MCValue(JPY, 12);
        MCValue v2 = new MCValue(JPY, 12.0);
        assertEquals(v1, v2);
    }

    public static void main(String[] args) {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
        MCValue mcv = new MCValue();
        System.out.println(mcv.getCurrency());
    }

}
