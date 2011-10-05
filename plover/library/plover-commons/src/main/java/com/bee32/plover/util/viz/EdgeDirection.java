package com.bee32.plover.util.viz;

public enum EdgeDirection {

    NONE("--"),

    UNI("->"),

    BOTH("<->"),

    ;

    String symbol;

    private EdgeDirection(String symbol) {
        if (symbol == null)
            throw new NullPointerException("symbol");
        this.symbol = symbol;
    }

}
