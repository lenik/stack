package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;

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
    public void readObject(IVariantMap<String> map)
            throws ParseException {
        super.readObject(map);
        salesOrderId = map.getInt("sdoc", salesOrderId);
    }

}
