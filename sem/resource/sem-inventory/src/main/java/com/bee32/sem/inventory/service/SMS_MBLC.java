package com.bee32.sem.inventory.service;

import java.util.Currency;

import javax.free.Nullables;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.StockLocation;

public class SMS_MBLC
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        final Material material;
        final BatchArray batchArray;
        final StockLocation location;
        final Currency currency;

        public Key(StockOrderItem item) {
            if (item == null)
                throw new NullPointerException("item");
            material = item.getMaterial();
            batchArray = item.getBatchArray();
            location = item.getLocation();
            currency = item.getPrice().getCurrency();
        }

        public Key(Material material, BatchArray batchArray, StockLocation location, Currency currency) {
            if (material == null)
                throw new NullPointerException("material");
            if (currency == null)
                throw new NullPointerException("currency");
            this.material = material;
            this.batchArray = batchArray;
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

            if (!Nullables.equals(batchArray, o.batchArray))
                return false;

            if (!currency.equals(o.currency))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            if (batchArray != null)
                hash += batchArray.hashCode();
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