package com.bee32.sem.world.monetary;

import java.io.Serializable;
import java.util.Currency;

/**
 * 汇率插值表缓存键
 *
 * <p lang="en">
 */
public class FxrMapKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Currency unit;
    final FxrUsage usage;

    public FxrMapKey(Currency unit, FxrUsage usage) {
        this.unit = unit;
        this.usage = usage;
    }

    public Currency getUnit() {
        return unit;
    }

    public FxrUsage getUsage() {
        return usage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FxrMapKey))
            return false;

        FxrMapKey o = (FxrMapKey) obj;

        if (!unit.equals(o.unit))
            return false;
        if (!usage.equals(o.usage))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += unit.hashCode();
        hash += usage.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return unit.getCurrencyCode() + "/" + usage;
    }

}
