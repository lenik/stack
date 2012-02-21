package com.bee32.sem.inventory.service;

import java.util.Currency;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.CBatch;

public class SMS_MBC
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;
        CBatch cbatch;
        Currency currency;

        public Key(Material material, CBatch cbatch, Currency currency) {
            if (material == null)
                throw new NullPointerException("material");
            if (cbatch == null)
                throw new NullPointerException("cbatch");
            if (currency == null)
                throw new NullPointerException("currency");
            this.material = material;
            this.cbatch = cbatch;
            this.currency = currency;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;

            if (!material.equals(o.material))
                return false;

            if (!cbatch.equals(o.cbatch))
                return false;

            if (!currency.equals(o.currency))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += cbatch.hashCode();
            hash += currency.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getCBatch(), item.getPrice().getCurrency());
    }

    public static final SMS_MBC INSTANCE = new SMS_MBC();

}