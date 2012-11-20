package com.bee32.sem.track.entity;

import com.bee32.plover.ox1.dict.NameDict;

public class ProductLine
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ProductLine() {
        super();
    }

    public ProductLine(String id, String label, String description) {
        super(id, label, description);
    }

    public ProductLine(String id, String label) {
        super(id, label);
    }

}
