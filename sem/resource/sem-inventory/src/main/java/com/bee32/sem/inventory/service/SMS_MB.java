package com.bee32.sem.inventory.service;

import javax.free.Nullables;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.util.BatchArray;

public class SMS_MB
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;
        BatchArray batchArray;

        public Key(Material material, BatchArray batchArray) {
            if (material == null)
                throw new NullPointerException("material");
            this.material = material;
            this.batchArray = batchArray;
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

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            if (batchArray != null)
                hash += batchArray.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getBatchArray());
    }

    public static final SMS_MB INSTANCE = new SMS_MB();

}