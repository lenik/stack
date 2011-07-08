package com.bee32.plover.orm.ext.types;

import static org.junit.Assert.fail;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public class MultiCurrencyTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        Locale zh = Locale.forLanguageTag("zh-CN");
        for (Currency cur : Currency.getAvailableCurrencies()) {
            String cc = cur.getCurrencyCode();
            String dn = cur.getDisplayName();
            String zn = cur.getDisplayName(zh);
            int nc = cur.getNumericCode();
            String s = String.format("[%s %4d] %s (%s)", cc, nc, dn, zn);
            System.out.println(s);
        }
    }

}
