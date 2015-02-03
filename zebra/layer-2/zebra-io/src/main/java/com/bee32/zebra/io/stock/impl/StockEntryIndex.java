package com.bee32.zebra.io.stock.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.stock.StockEntry;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 库存相关的具体操作，是库存作业的最小组成部分。
 * 
 * @label 库存操作
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(StockEntry.class)
public class StockEntryIndex
        extends QuickIndex {

    public StockEntryIndex(IQueryable context) {
        super(context);
    }

}
