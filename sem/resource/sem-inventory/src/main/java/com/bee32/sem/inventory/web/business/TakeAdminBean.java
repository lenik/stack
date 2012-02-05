package com.bee32.sem.inventory.web.business;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.entity.StockOrder;

@ForEntity(value = StockOrder.class, parameters = //
@TypeParameter(name = "_subject", value = { "TK_I", "TK_O", "TKFI", "TKFO" }))
public class TakeAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public TakeAdminBean() {
    }

}
