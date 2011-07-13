package com.bee32.sem.world.monetary;

import java.util.Collection;

public interface ExchangeRateTable {

    Collection<String> getAvailableCurrencies();

    Float lookup(String code);

}
