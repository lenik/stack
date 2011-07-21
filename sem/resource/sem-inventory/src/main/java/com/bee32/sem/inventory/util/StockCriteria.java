package com.bee32.sem.inventory.util;

import java.util.Arrays;

import org.hibernate.criterion.Criterion;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;

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
     * 非冗余的库存订单。
     * <p>
     * 在线性统计库存变更集中，需要排除冗余的库存订单。
     */
    @LeftHand(StockOrder.class)
    public static CriteriaElement unpacked() {
        return not(in("subject", Arrays.asList(//
                StockOrderSubject.PACK_M.getValue(), //
                StockOrderSubject.PACK_MB.getValue(), //
                StockOrderSubject.PACK_MBL.getValue(), //
                StockOrderSubject.PACK_MC.getValue(), //
                StockOrderSubject.PACK_MBC.getValue(), //
                StockOrderSubject.PACK_MBLC.getValue() //
                )));
    }

    /**
     * TODO
     */
    @LeftHand(StockOrderItem.class)
    public static Criterion peerOf(StockOrderItem item) {
        return null;
    }

}
