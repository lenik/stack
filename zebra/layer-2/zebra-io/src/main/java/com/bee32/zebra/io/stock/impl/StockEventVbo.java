package com.bee32.zebra.io.stock.impl;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class StockEventVbo
        extends FooMesgVbo<StockEvent> {

    public StockEventVbo() {
        super(StockEvent.class);
    }

}
