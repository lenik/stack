package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;

public class MBPacking
        implements IStockMergeStrategy {

    static final class Key {

        Material material;
        String cbatch;

        public Key(Material material, String cbatch) {
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

    public static final MBPacking INSTANCE = new MBPacking();

}