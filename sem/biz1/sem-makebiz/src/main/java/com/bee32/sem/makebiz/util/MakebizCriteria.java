package com.bee32.sem.makebiz.util;

import java.util.Date;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.MakeOrder;

public class MakebizCriteria
        extends CriteriaSpec {

    @LeftHand(MakeOrder.class)
    public static ICriteriaElement customerLike(String pattern) {
        return compose(alias("customer", "customer"),//
                likeIgnoreCase("customer.label", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(DeliveryNote.class)
    public static ICriteriaElement correspondingTicket(Long ticketId) {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new Equals("ticket.id", ticketId));
    }

    public static ICriteriaElement existingMaterialCheck(String label, String modelSpec) {
        return and(equals("label", label), equals("modelSpec", modelSpec));
    }

    public static ICriteriaElement existingPartCheck(String label, String modelSpec) {
        return compose(alias("target", "material"), equals("material.label", label),
                equals("material.modelSpec", modelSpec));
    }

    public static ICriteriaElement dateRangeRestriction(String propertyName, Date start, Date end) {
        return and(greaterOrEquals(propertyName, start), lessThan(propertyName, end));
    }
}
