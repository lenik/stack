package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.free.Nullables;

import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.material.entity.Material;

/**
 * 库存项目关键字
 *
 * <p lang="en">
 * Stock Item Key
 */
public class StockItemKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Material material;
    final BatchArray batchArray;

    public StockItemKey(Material material, BatchArray batchArray) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
        this.batchArray = batchArray;
    }

    public Material getMaterial() {
        return material;
    }

    public BatchArray getCbatch() {
        return batchArray;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StockItemKey))
            return false;

        StockItemKey o = (StockItemKey) obj;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('<');
        sb.append(material.getId());
        sb.append(':');
        sb.append(material.getName());
        sb.append(',');
        sb.append(batchArray);
        sb.append('>');
        return sb.toString();
    }

}
