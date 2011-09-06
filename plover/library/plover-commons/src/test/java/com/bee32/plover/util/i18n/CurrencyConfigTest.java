package com.bee32.plover.util.i18n;

import java.util.Currency;

import org.junit.Assert;

import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.plover.util.i18n.ICurrencyAware;
import com.bee32.plover.util.i18n.ILocaleAware;

public class CurrencyConfigTest
        extends Assert
        implements ICurrencyAware, ILocaleAware {

    public static void main(String[] args) {
        for (Currency c : CurrencyConfig.list())
            System.out.println(CurrencyConfig.format(c, zh_CN));
    }

}
