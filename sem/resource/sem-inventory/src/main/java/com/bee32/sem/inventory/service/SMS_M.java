package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.material.entity.Material;

public class SMS_M
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;

        public Key(Material material) {
            if (material == null)
                throw new NullPointerException("material");
            this.material = material;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;
            return material.equals(o.material);
        }

        @Override
        public int hashCode() {
            return material.hashCode();
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial());
    }

    public static final SMS_M INSTANCE = new SMS_M();

}