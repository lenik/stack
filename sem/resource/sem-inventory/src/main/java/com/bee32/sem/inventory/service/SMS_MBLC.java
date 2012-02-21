package com.bee32.sem.inventory.service;

import java.util.Currency;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.CBatch;

public class SMS_MBLC
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        final Material material;
        final CBatch cBatch;
        final StockLocation location;
        final Currency currency;

        public Key(StockOrderItem item) {
            if (item == null)
                throw new NullPointerException("item");
            material = item.getMaterial();
            cBatch = item.getCBatch();
            location = item.getLocation();
            currency = item.getPrice().getCurrency();
        }

        public Key(Material material, CBatch cBatch, StockLocation location, Currency currency) {
            if (material == null)
                throw new NullPointerException("material");
            if (cBatch == null)
                throw new NullPointerException("cbatch");
            if (currency == null)
                throw new NullPointerException("currency");
            this.material = material;
            this.cBatch = cBatch;
            this.location = location;
            this.currency = currency;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;

            if (!material.equals(o.material))
                return false;

            if (!cBatch.equals(o.cBatch))
                return false;

            if (!currency.equals(o.currency))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += cBatch.hashCode();
            hash += currency.hashCode();
            return hash;
        }

    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item);
    }

    public static final SMS_MBLC INSTANCE = new SMS_MBLC();

}