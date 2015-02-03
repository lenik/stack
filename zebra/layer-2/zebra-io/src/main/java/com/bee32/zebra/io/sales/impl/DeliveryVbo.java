package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.site.IBasicSiteAnchors;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class DeliveryVbo
        extends SlimMesgForm_htm<Delivery>
        implements IBasicSiteAnchors {

    public DeliveryVbo() {
        super(Delivery.class);
    }

}
