package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;
import java.util.List;

public interface IFxrProvider {

    /**
     * This is the same to {@link FxrTable#getQuoteCurrency()}.
     *
     * The actual provider should use the native currency as quote currency.
     *
     * @return Non-<code>null</code> quote currency in use.
     */
    Currency getQuoteCurrency();

    /**
     * Get the FXR table towards the floor and several tables before of a specific date.
     *
     * @return The FXR table towards the floor of a specific date. <code>null</code> if none.
     */
    List<FxrTable> getFxrTableSeries(Date date, int nprev)
            throws FxrQueryException;

    /**
     * Get the FXR table towards the floor of a specific date.
     *
     * @return The FXR table towards the floor of a specific date. <code>null</code> if none.
     */
    FxrTable getFxrTable(Date date)
            throws FxrQueryException;

    /**
     * Get the FXR of a specific unit currency towards the floor of a specific date.
     *
     * @return <code>null</code> if none.
     */
    Float getFxr(Date date, Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException;

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
     * @param unitCurrency
     *            The unit currency to be queried.
     * @return <code>null</code> if the FXR for the specified unit currency is not defined.
     */
    Float getLatestFxr(Currency unitCurrency, FxrUsage usage)
            throws FxrQueryException;

}
