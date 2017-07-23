package com.bee32.zebra.io.stock.impl;

import java.util.List;

import net.bodz.lily.model.util.F_YearCount;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.sql.FooMapper;

public interface StockEventMapper
        extends FooMapper<StockEvent, StockEventMask> {

    List<F_YearCount> histoByYear();

}
