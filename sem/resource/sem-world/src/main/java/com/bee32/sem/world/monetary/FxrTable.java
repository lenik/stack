package com.bee32.sem.world.monetary;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

public class FxrTable
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date version;
    Currency target;
    MCArray array;

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        if (version == null)
            throw new NullPointerException("version");
        this.version = version;
    }

    public Currency getTarget() {
        return target;
    }

    public void setTarget(Currency target) {
        if (target == null)
            throw new NullPointerException("target");
        this.target = target;
    }

    public MCArray getArray() {
        return array;
    }

    public void setArray(MCArray array) {
        if (array == null)
            throw new NullPointerException("array");
        this.array = array;
    }

}
