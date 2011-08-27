package com.bee32.sem.purchase.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 采购请求/采购申请/采购计划
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    MaterialPlan plan;

    @NaturalId
    @OneToOne(optional = false)
    public MaterialPlan getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlan plan) {
        if (plan == null)
            throw new NullPointerException("plan");
        this.plan = plan;
    }

    @Override
    protected Serializable naturalId() {
        return naturalId(plan);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selector(prefix + "plan", plan);
    }

}
