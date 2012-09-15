package com.bee32.sem.salary.util;

import java.io.Serializable;

public class ColumnModel
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String header;
    int order;
    long defId;

    public ColumnModel(String header, int order, long defId) {
        this.header = header;
        this.order = order;
        this.defId = defId;
    }

    public String getHeader() {
        return header;
    }

    public int getOrder() {
        return order;
    }

    public long getDefId() {
        return defId;
    }
}
