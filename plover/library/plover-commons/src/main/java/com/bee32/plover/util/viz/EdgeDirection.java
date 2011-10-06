package com.bee32.plover.util.viz;

public enum EdgeDirection {

    NONE("--"),

    UNI("->"),

    BOTH("<->"),

    ;

    final String symbol;

    private EdgeDirection(String symbol) {
        if (symbol == null)
            throw new NullPointerException("symbol");
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
