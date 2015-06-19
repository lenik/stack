package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoObjectCriteria;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.sales.DeliveryItem
 */
public class DeliveryItemCriteria
        extends CoObjectCriteria {

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
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        deliveryId = map.getInt("doc", deliveryId);
        salesOrderId = map.getInt("sdoc", salesOrderId);
    }

}
