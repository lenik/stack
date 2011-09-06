package com.bee32.plover.util.i18n;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.util.i18n.CurrencyNames;
import com.bee32.plover.util.i18n.ICurrencyAware;

public class CurrencyNamesTest
        extends Assert
        implements ICurrencyAware {

    @Test
    public void testRevMap_CN() {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Map<String, Currency> revMap = CurrencyNames.getRevMap(zh);
        assertNotNull(revMap);
        // System.out.println(revMap);
        Currency actual = revMap.get("英镑");
        assertEquals(GBP, actual);
    }

}
