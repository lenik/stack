package com.bee32.zebra.io.stock.impl;

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class StockEntryVbo
        extends SlimForm_htm<StockEntry> {

    public StockEntryVbo() {
        super(StockEntry.class);
    }

}
