package com.bee32.plover.ox1.xp;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

@Entity
public class XPoolModel
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public XPoolModel() {
        super();
    }

    public XPoolModel(String name, String label) {
        super(name, label);
    }

    public XPoolModel(String name, String label, String description) {
        super(name, label, description);
    }

}
