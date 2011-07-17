package com.bee32.sem.world.monetary;

/**
 * 汇率更新程序
 */
public interface IFxrUpdater {

    /**
     * Get the preferred interval.
     *
     * @return Interval in minutes.
     */
    int getPreferredInterval();

    /**
     * Commit the FXR updates.
     */
    void update()
            throws FxrQueryException;

}
