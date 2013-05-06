package com.bee32.sem.asset.service.balance_sheet;


public class Value21A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value21Assistant.buildQueryString(verified);
    }
}
