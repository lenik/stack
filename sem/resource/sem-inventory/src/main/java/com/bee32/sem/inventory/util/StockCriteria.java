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
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.process.verify.util.VerifyCriteria;

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
                CommonCriteria.betweenEx("out.createdDate", from, to));
    }

    /**
     * @param subjects
     *            Limit to these subjects.
     * @param materials
     *            Specify <code>null</code> to select all materials.
     */
    @LeftHand(Object.class)
    public static ICriteriaElement sum(Set<String> subjects, List<Material> materials, StockQueryOptions options) {
        if (subjects == null)
            throw new NullPointerException("subjects");

        return compose(//
                alias("parent", "parent"), //
                lessOrEquals("parent.beginTime", options.getTimestamp()), //
                options.isVerifiedOnly() ? VerifyCriteria.verified("parent") : null, //
                in("parent._subject", subjects), //
                materials == null ? null : in("material", materials), // _in
                _equals("CBatch", options.getCBatch()), //
                _equals("location", options.getLocation()), //
                _equals("warehouse", options.getWarehouse()));
    }

    public static ICriteriaElement sumOfCommons(List<Material> materials, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getCommonSet();
        return sum(subjects, materials, options);
    }

    public static ICriteriaElement sumOfVirtuals(List<Material> materials, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getVirtualSet();
        return sum(subjects, materials, options);
    }

    public static ICriteriaElement sumOfVirtualOnly(List<Material> materials, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getVirtualOnlySet();
        return sum(subjects, materials, options);
    }

    @LeftHand(StockOrderItem.class)
    public static ICriteriaElement inOutDetail(Date beginDate, Long materialId, StockQueryOptions options) {
        return compose(alias("parent", "parent"), //
                options.isVerifiedOnly() ? VerifyCriteria.verified("parent") : null, //
                lessOrEquals("parent.beginTime", options.getTimestamp()), //
                _equals("material.id", materialId), //
                _equals("CBatch", options.getCBatch()), //
                _equals("location", options.getLocation()), //
                _equals("warehouse", options.getWarehouse()));
    }
}
