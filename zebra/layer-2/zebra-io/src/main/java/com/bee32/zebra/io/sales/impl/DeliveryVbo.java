package com.bee32.zebra.io.sales.impl;

import net.bodz.bas.site.IBasicSiteAnchors;

import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class DeliveryVbo
        extends FooMesgVbo<Delivery>
        implements IBasicSiteAnchors {

    public DeliveryVbo() {
        super(Delivery.class);
    }

}
