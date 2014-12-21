package com.bee32.zebra.io.stock.impl;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.sea.FooVbo;

public class StockEventVbo
        extends FooVbo<StockEvent> {

    public StockEventVbo() {
        super(StockEvent.class);
    }

}
