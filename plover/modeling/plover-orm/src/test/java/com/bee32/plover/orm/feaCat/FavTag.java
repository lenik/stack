package com.bee32.plover.orm.feaCat;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class FavTag<T>
        extends SuperEntity<Integer> {

    private static final long serialVersionUID = 1L;

    T who;
    String tag;

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
