package com.bee32.sem.inventory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.criteria.hibernate.CriteriaTemplate;
import com.bee32.plover.criteria.hibernate.QueryEntity;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockSnapshot;

public class StockCriteria
        extends CriteriaTemplate {

    /**
     * 基于某快照。
     */
    @QueryEntity({ StockOrder.class, StockSnapshot.class })
    public static Criterion basedOn(StockSnapshot snapshot) {
        if (snapshot == null)
            throw new NullPointerException("snapshot");
        return Restrictions.eq("base.id", snapshot.getId());
    }

    /**
     * 基于某快照、该快照的祖先快照等。
     */
    @QueryEntity({ StockOrder.class, StockSnapshot.class })
    public static Criterion basedUpon(StockSnapshot snapshot) {
        List<Integer> parentIds = new ArrayList<Integer>();

        StockSnapshot node = snapshot;
        while (node != null) {
            parentIds.add(node.getId());
            node = node.getParent();
        }

        return Restrictions.in("base.id", parentIds);
    }

    /**
     * 非冗余的库存订单。
     * <p>
     * 在线性统计库存变更集中，需要排除冗余的库存订单。
     */
    @QueryEntity(StockOrder.class)
    public static Criterion unpacked() {
        return Restrictions.ne("subject", Arrays.asList(//
                StockOrderSubject.PACK_M.getValue(), //
                StockOrderSubject.PACK_MB.getValue(), //
                StockOrderSubject.PACK_MC.getValue(), //
                StockOrderSubject.PACK_MBC.getValue() //
                ));
    }

    /**
     * TODO
     */
    @QueryEntity(StockOrderItem.class)
    public static Criterion peerOf(StockOrderItem item) {
        return null;
    }

}
