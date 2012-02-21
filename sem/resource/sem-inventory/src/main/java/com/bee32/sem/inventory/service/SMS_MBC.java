package com.bee32.sem.inventory.service;

import java.util.Currency;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.BatchArray;

public class SMS_MBC
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;
        BatchArray batchArray;
        Currency currency;

        public Key(Material material, BatchArray batchArray, Currency currency) {
            if (material == null)
                throw new NullPointerException("material");
            if (batchArray == null)
                throw new NullPointerException("batchArray");
            if (currency == null)
                throw new NullPointerException("currency");
            this.material = material;
            this.batchArray = batchArray;
            this.currency = currency;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;

            if (!material.equals(o.material))
                return false;

            if (!batchArray.equals(o.batchArray))
                return false;

            if (!currency.equals(o.currency))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += batchArray.hashCode();
            hash += currency.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getBatchArray(), item.getPrice().getCurrency());
    }

    public static final SMS_MBC INSTANCE = new SMS_MBC();

}