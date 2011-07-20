package com.bee32.sem.inventory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockSnapshot;

public class StockCriteria
        extends CriteriaSpec {

    /**
     * 基于某快照。
     */
    @LeftHand({ StockOrder.class, StockSnapshot.class })
    public static CriteriaElement isBasedOn(StockSnapshot snapshot) {
        if (snapshot == null)
            throw new NullPointerException("snapshot");
        return equals("base.id", snapshot.getId());
    }

    /**
     * 基于某快照、该快照的祖先快照等。
     */
    @LeftHand({ StockOrder.class, StockSnapshot.class })
    public static CriteriaElement isBasedUpon(StockSnapshot snapshot) {
        List<Integer> parentIds = new ArrayList<Integer>();

        StockSnapshot node = snapshot;
        while (node != null) {
            parentIds.add(node.getId());
            node = node.getParent();
        }

        return in("base.id", parentIds);
    }

    /**
     * 非冗余的库存订单。
     * <p>
     * 在线性统计库存变更集中，需要排除冗余的库存订单。
     */
    @LeftHand(StockOrder.class)
    public static CriteriaElement isUnpacked() {
        return notEquals("subject", Arrays.asList(//
                StockOrderSubject.PACK_M.getValue(), //
                StockOrderSubject.PACK_MB.getValue(), //
                StockOrderSubject.PACK_MC.getValue(), //
                StockOrderSubject.PACK_MBC.getValue() //
                ));
    }

    /**
     * TODO
     */
    @LeftHand(StockOrderItem.class)
    public static Criterion peerOf(StockOrderItem item) {
        return null;
    }

}
