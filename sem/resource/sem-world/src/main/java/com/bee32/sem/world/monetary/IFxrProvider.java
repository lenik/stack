package com.bee32.sem.world.monetary;

import java.io.IOException;
import java.util.Currency;

public interface IFxrProvider {

    /**
     * @return <code>null</code> if the currency
     */
    Double lookup(Currency currency)
            throws IOException;

}
