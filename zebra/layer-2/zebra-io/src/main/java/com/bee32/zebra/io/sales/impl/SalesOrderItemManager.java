package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class SalesOrderItemManager
        extends CoEntityManager {

    public SalesOrderItemManager(IQueryable context) {
        super(SalesOrderItem.class, context);
    }

}
