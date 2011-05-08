package com.bee32.plover.orm.ext.xp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.entity.EntityBean;

public abstract class EntityExt<K extends Serializable, X extends XPool<?>>
        extends EntityBean<K>
        implements IXPoolable<X> {

    private static final long serialVersionUID = 1L;

    List<X> xPool;

    public EntityExt() {
        super();
    }

    public EntityExt(String name) {
        super(name);
    }

    protected abstract List<X> getXPool();

    protected abstract void setXPool(List<X> xPool);

    @Override
    public final List<X> pool() {
        if (xPool == null) {
            synchronized (this) {
                if (xPool == null) {
                    xPool = new ArrayList<X>();
                }
            }
        }
        return xPool;
    }

    protected final void pool(List<X> xPool) {
        this.xPool = xPool;
    }

}
