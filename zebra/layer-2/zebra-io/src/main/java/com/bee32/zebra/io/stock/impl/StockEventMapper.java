package com.bee32.zebra.io.stock.impl;

import java.util.List;

import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.io.stock.StockEvent;
import com.bee32.zebra.tk.util.F_YearCount;

public interface StockEventMapper
        extends IMapperTemplate<StockEvent, StockEventCriteria> {

    List<F_YearCount> histoByYear();

}
