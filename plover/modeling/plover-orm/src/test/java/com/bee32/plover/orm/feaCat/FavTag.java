package com.bee32.plover.orm.feaCat;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class FavTag<T>
        extends SuperEntity<Integer> {

    private static final long serialVersionUID = 1L;

    T who;
    String tag;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof FavTag)
            _populate((FavTag<T>) source);
        else
            super.populate(source);
    }

    protected void _populate(FavTag<T> o) {
        super._populate(o);
        who = o.who;
        tag = o.tag;
    }

    @ManyToOne
    public T getWho() {
        return who;
    }

    public void setWho(T peer) {
        this.who = peer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
