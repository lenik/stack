package com.bee32.plover.ox1.xp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CloneUtils;
import com.bee32.plover.ox1.color.UIEntityAuto;

public abstract class EntityExt<K extends Serializable, X extends XPool<?>>
        extends UIEntityAuto<K>
        implements IXPoolable<X> {

    private static final long serialVersionUID = 1L;

    List<X> xPool;

    public EntityExt() {
        super();
    }

    public EntityExt(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof EntityExt)
            _populate((EntityExt<K, X>) source);
        else
            super.populate(source);
    }

    protected void _populate(EntityExt<K, X> o) {
        super._populate(o);
        xPool = CloneUtils.cloneList(o.xPool);
    }

    @OneToMany
    @Cascade(CascadeType.ALL)
    final List<X> getXPool() {
        return pool();
    }

    void setXPool(List<X> xPool) {
        this.xPool = xPool;
    }

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

}
