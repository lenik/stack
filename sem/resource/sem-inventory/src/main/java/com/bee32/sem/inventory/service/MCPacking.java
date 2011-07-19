package com.bee32.sem.inventory.service;

import java.util.Currency;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;

public class MCPacking
        implements IStockMergeStrategy {

    static final class Key {

        Material material;
        Currency currency;

        public Key(Material material, Currency currency) {
            if (material == null)
                throw new NullPointerException("material");
            if (currency == null)
                throw new NullPointerException("currency");
            this.material = material;
            this.currency = currency;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;

            if (!material.equals(o.material))
                return false;

            if (!currency.equals(o.currency))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += currency.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getPrice().getCurrency());
    }

    public static final MCPacking INSTANCE = new MCPacking();

}