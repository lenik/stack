package com.bee32.zebra.io.stock.impl;

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.sea.FooVbo;

public class StockEntryVbo
        extends FooVbo<StockEntry> {

    public StockEntryVbo() {
        super(StockEntry.class);
    }

}
