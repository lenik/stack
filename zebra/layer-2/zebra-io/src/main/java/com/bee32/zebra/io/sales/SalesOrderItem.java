package com.bee32.zebra.io.sales;

import java.util.Date;

import com.bee32.zebra.io.art.Artifact;
import com.tinylily.model.base.CoMomentInterval;

public class SalesOrderItem
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    long id;
    SalesOrder order;
    Artifact artifact;

    String altLabel;
    String altSpec;
    String altUom;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return super.getBeginDate();
    }

    public Date getDeadline() {
        return super.getEndDate();
    }

}