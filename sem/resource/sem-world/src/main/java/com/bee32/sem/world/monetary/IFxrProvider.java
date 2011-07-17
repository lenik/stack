package com.bee32.sem.world.monetary;

import java.util.Currency;

public interface IFxrProvider {

    /**
     * Get the latest FXR table.
     *
     * @return Non-<code>null</code> {@link FxrTable}.
     */
    FxrTable getLatestFxrTable()
            throws FxrQueryException;

    /**
     * Get the latest FXR entry.
     * <p>
     * The same to {@link #getLatestFxrTable()}, {@link FxrTable#getQuote(Currency)}.
     *
     * @param quoteCurrency
     *            The quoting currency to be queried.
     * @return <code>null</code> if the FXR for the specified quote currency is not defined.
     */
    Float getLatestFxr(Currency quoteCurrency)
            throws FxrQueryException;

}
