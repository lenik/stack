package com.bee32.sem.asset.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
public class AssetSubject
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    boolean negative;
    String orderName;

    public AssetSubject() {
        super();
    }

    public AssetSubject(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    public AssetSubject(int order, String name, String label) {
        super(order, name, label);
    }

    public AssetSubject(String name, String label, String description) {
        super(name, label, description);
    }

    public AssetSubject(String name, String label) {
        super(name, label);
    }

}
