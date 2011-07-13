package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.util.Currency;

import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.IFxrUpdater;

public abstract class OnlineFxrUpdater
        implements IFxrUpdater {

    /**
     * 获取最新的汇率表。
     */
    protected abstract FxrTable download(Currency target)
            throws IOException;

}
