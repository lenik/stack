package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 库存相关的具体操作，是库存作业的最小组成部分。
 * 
 * @label 库存操作
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@PathToken("stentry")
public class StockEntryManager
        extends FooManager {

    public StockEntryManager(IQueryable context) {
        super(StockEntry.class, context);
    }

}
