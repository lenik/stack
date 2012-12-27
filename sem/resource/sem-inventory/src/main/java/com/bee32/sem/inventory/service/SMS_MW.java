package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.StockWarehouse;

public class SMS_MW
        implements IStockMergeStrategy {

    private static final long serialVersionUID = 1L;

    static final class Key {

        Material material;
        StockWarehouse warehouse;

        public Key(Material material, StockWarehouse warehouse) {
            if (material == null)
                throw new NullPointerException("material");
            if (warehouse == null)
                throw new NullPointerException("warehouse");
            this.material = material;
            this.warehouse = warehouse;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key)
                return false;
            Key o = (Key) obj;

            if (!material.equals(o.material))
                return false;

            if (!warehouse.equals(o.warehouse))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += material.hashCode();
            hash += warehouse.hashCode();
            return hash;
        }
    }

    @Override
    public Object getMergeKey(StockOrderItem item) {
        return new Key(item.getMaterial(), item.getWarehouse());
    }

    public static final SMS_MB INSTANCE = new SMS_MB();

}