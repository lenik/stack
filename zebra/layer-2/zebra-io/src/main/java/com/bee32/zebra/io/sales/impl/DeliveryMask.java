package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.sea.QVariantMap;

import com.bee32.zebra.tk.sea.MyMask;

/**
 * @see com.bee32.zebra.io.sales.Delivery
 */
public class DeliveryMask
        extends MyMask {

    public Integer salesOrderId;

    public Integer getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Integer salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        salesOrderId = map.getInt("sdoc", salesOrderId);
    }

}
