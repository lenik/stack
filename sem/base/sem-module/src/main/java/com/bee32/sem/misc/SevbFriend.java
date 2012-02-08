package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.faces.view.ViewBean;

public abstract class SevbFriend
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    /**
     * @return friend entry.
     */
    public Serializable select(SimpleEntityViewBean sevb, Object mainEntry) {
        return null;
    }

    public void unselect(SimpleEntityViewBean sevb, Serializable friendEntry) {
    }

    public abstract Serializable open(SimpleEntityViewBean sevb, Object mainObject);

    public abstract void save(SimpleEntityViewBean sevb, Object mainObject, Serializable friendObject);

    public abstract void delete(SimpleEntityViewBean sevb, Object mainObject, Serializable friendObject);

}
