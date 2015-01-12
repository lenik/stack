package com.bee32.zebra.io.stock.impl;

import java.util.List;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_YearCount;

public interface StockEventMapper
        extends FooMapper<StockEvent, StockEventCriteria> {

    List<F_YearCount> histoByYear();

}
