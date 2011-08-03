package com.bee32.sem.world.monetary;

import java.io.IOException;

/**
 * 汇率更新程序
 */
public interface IFxrSource {

    /**
     * Get the preferred interval.
     *
     * @return Interval in minutes.
     */
    int getPreferredInterval();

    /**
     * Download for the latest fxr table.
     *
     * @return <code>null</code> if no update. I.e., there's nothing to be updated.
     */
    FxrTable download()
            throws IOException;

}
