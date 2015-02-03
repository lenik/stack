package com.bee32.zebra.io.stock.impl;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class StockEventVbo
        extends SlimMesgForm_htm<StockEvent> {

    public StockEventVbo() {
        super(StockEvent.class);
    }

}
