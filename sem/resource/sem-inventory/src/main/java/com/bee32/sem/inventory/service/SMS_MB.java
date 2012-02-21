package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.CBatch;

public class SMS_MB
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;
        CBatch cbatch;

        public Key(Material material, CBatch cbatch) {
            if (material == null)
                throw new NullPointerException("material");
            if (cbatch == null)
                throw new NullPointerException("cbatch");
            this.material = material;
            this.cbatch = cbatch;
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

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += cbatch.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getCBatch());
    }

    public static final SMS_MB INSTANCE = new SMS_MB();

}