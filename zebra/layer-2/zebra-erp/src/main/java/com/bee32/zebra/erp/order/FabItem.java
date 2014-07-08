package com.bee32.zebra.erp.order;

import java.util.Date;

import com.bee32.zebra.erp.stock.Artifact;
import com.tinylily.model.base.CoMomentInterval;

public class FabItem
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    FabOrder order;
    Artifact artifact;

    String altLabel;
    String altSpec;
    String altUom;

    public Date getOrderTime() {
        return super.getBeginTime();
    }

    public Date getDeadline() {
        return super.getEndTime();
    }

}