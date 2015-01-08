package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(SalesOrderItem.class)
public class SalesOrderItemIndex
        extends FooIndex {

    public SalesOrderItemIndex(IQueryable context) {
        super(context);
    }

}
