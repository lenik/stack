package com.bee32.sem.world.monetary;

import java.util.Currency;

import org.junit.Assert;

public class CurrencyConfigTest
        extends Assert
        implements ICurrencyAware, ILocaleAware {

    public static void main(String[] args) {
        for (Currency c : CurrencyConfig.list())
            System.out.println(CurrencyConfig.format(c, zh_CN));
    }

}
