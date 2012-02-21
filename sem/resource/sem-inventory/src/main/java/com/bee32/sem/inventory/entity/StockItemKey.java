package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.free.Nullables;

import com.bee32.sem.inventory.util.CBatch;

public class StockItemKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Material material;
    final CBatch cbatch;

    public StockItemKey(Material material, CBatch cbatch) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
        this.cbatch = cbatch;
    }

    public Material getMaterial() {
        return material;
    }

    public CBatch getCbatch() {
        return cbatch;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StockItemKey))
            return false;

        StockItemKey o = (StockItemKey) obj;
        if (!material.equals(o.material))
            return false;

        if (!Nullables.equals(cbatch, o.cbatch))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += material.hashCode();
        if (cbatch != null)
            hash += cbatch.hashCode();
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
        sb.append(cbatch);
        sb.append('>');
        return sb.toString();
    }

}
