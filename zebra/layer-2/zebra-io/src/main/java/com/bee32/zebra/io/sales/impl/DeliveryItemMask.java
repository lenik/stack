package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.lily.model.base.CoObjectMask;

/**
 * @see com.bee32.zebra.io.sales.DeliveryItem
 */
public class DeliveryItemMask
        extends CoObjectMask {

    public Integer deliveryId;
    public Integer salesOrderId;

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

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
        deliveryId = map.getInt("doc", deliveryId);
        salesOrderId = map.getInt("sdoc", salesOrderId);
    }

}
