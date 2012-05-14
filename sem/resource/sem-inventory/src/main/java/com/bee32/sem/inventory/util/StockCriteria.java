package com.bee32.sem.inventory.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.process.verify.util.VerifyCriteria;
import com.bee32.sem.world.monetary.MonetaryCriteria;

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

    static final List<Long> emptyIds = Arrays.asList(-1L);

    /**
     * @param subjects
     *            Limit to these subjects.
     * @param materials
     *            Specify <code>null</code> to select all materials.
     */
    @LeftHand(Object.class)
    public static ICriteriaElement sum(Set<String> subjects, List<Long> materialIds, StockQueryOptions options) {
        if (subjects == null)
            throw new NullPointerException("subjects");
        if (subjects.isEmpty())
            throw new IllegalArgumentException("No subject specified.");

        if (materialIds != null && materialIds.isEmpty())
            materialIds = emptyIds;

        return compose(//
                alias("parent", "parent"), //
                lessOrEquals("parent.beginTime", options.getTimestampOpt()), //
                options.isVerifiedOnly() ? VerifyCriteria.verified("parent") : null, //
                in("parent._subject", subjects), //
                materialIds == null ? null : in("material.id", materialIds), // _in
                _equals("BatchArray", options.getBatchArray()), //
                _equals("location.id", options.getLocation()), //
                _equals("warehouse.id", options.getWarehouse()));
    }

    public static ICriteriaElement sumOfCommons(List<Long> materialIds, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getCommonSet();
        return sum(subjects, materialIds, options);
    }

    public static ICriteriaElement sumOfVirtuals(List<Long> materialIds, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getVirtualSet();
        return sum(subjects, materialIds, options);
    }

    public static ICriteriaElement sumOfVirtualOnly(List<Long> materialIds, StockQueryOptions options) {
        Set<String> subjects = StockOrderSubject.getVirtualOnlySet();
        return sum(subjects, materialIds, options);
    }

    @LeftHand(StockOrderItem.class)
    public static ICriteriaElement inOutDetail(Date beginDate, Long materialId, StockQueryOptions options) {
        CriteriaComposite comp = compose(alias("parent", "parent"), //
                _equals("material.id", materialId), //
                options.isVerifiedOnly() ? VerifyCriteria.verified("parent") : null, //
                lessOrEquals("parent.beginTime", options.getTimestampOpt()), //
                MonetaryCriteria.equals("price", options.getPrice()), //
                _equals("location.id", options.getLocation()), //
                _equals("warehouse.id", options.getWarehouse()));

        BatchArray batchArray = options.getBatchArray();
        if (batchArray != null) {
            BatchMetadata metadata = BatchMetadata.getInstance();
            for (int i = 0; i < metadata.getArraySize(); i++) {
                String batch = batchArray.getBatch(i);
                if (batch != null)
                    comp.add(_equals("batchArray.batch" + i, batch));
            }
        }
        return comp;
    }

    @LeftHand(StockOrder.class)
    public static ICriteriaElement test (String pattern){
        //return sqlRestriction("select * from sotck_order");
        return compose(alias("items", "item"),
                alias("item.material", "mat"),
                likeIgnoreCase("mat.label", pattern, MatchMode.ANYWHERE)
                );
    }
}
