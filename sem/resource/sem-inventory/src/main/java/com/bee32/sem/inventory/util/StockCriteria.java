package com.bee32.sem.inventory.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.misc.EntityCriteria;

public class StockCriteria
        extends CriteriaSpec {

    /**
     * 基于某快照。
     */
    @LeftHand({ StockOrder.class, StockPeriod.class })
    public static CriteriaElement basedOn(StockPeriod period) {
        if (period == null)
            throw new NullPointerException("snapshot");
        return equals("base.id", period.getId());
    }

    /**
     * 非冗余的库存订单。
     * <p>
     * 在线性统计库存变更集中，需要排除冗余的库存订单。
     */
    @LeftHand(StockOrder.class)
    public static CriteriaElement unpacked() {
        return not(in("_subject", Arrays.asList(//
                StockOrderSubject.PACK_M.getValue(), //
                StockOrderSubject.PACK_MB.getValue(), //
                StockOrderSubject.PACK_MBL.getValue(), //
                StockOrderSubject.PACK_MC.getValue(), //
                StockOrderSubject.PACK_MBC.getValue(), //
                StockOrderSubject.PACK_MBLC.getValue() //
                )));
    }

    @LeftHand(StockOrder.class)
    public static CriteriaElement subjectOf(StockOrderSubject subject) {
        return new Equals("_subject", subject.getValue());
    }

    /**
     * TODO
     */
    @LeftHand(StockOrderItem.class)
    public static Criterion peerOf(StockOrderItem item) {
        return null;
    }

    @LeftHand(StockOutsourcing.class)
    public static ICriteriaElement danglingOutsourcing(Date from, Date to) {
        return compose(alias("output", "out"), //
                isNull("input"), //
                equals("out._subject", StockOrderSubject.OSP_OUT.getValue()), //
                EntityCriteria.betweenEx("out.createdDate", from, to));
    }

    @LeftHand(Object.class)
    public static ICriteriaElement sum(Set<String> subjects, Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        if (subjects == null)
            throw new NullPointerException("subjects");
        if (date == null)
            throw new NullPointerException("date");
        if (materials == null)
            throw new NullPointerException("materials");

        ICriteriaElement _cbatch = null;
        ICriteriaElement _location = null;
        if (cbatch != null)
            _cbatch = equals("cbatch", cbatch);
        if (location != null)
            _location = equals("location", location);
        return compose(//
                lessOrEquals("beginDate", date), //
                not(in("_subject", subjects)), //
                in("material", materials), //
                _cbatch, //
                _location);
    }

    public static ICriteriaElement sumOfCommons(Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        return sum(StockOrderSubject.getCommonSet(), date, materials, cbatch, location);
    }

    public static ICriteriaElement sumOfVirtuals(Date date, List<Material> materials, String cbatch,
            StockLocation location) {
        return sum(StockOrderSubject.getVirtualSet(), date, materials, cbatch, location);
    }

}
