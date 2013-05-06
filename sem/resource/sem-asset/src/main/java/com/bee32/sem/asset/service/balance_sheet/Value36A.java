package com.bee32.sem.asset.service.balance_sheet;


public class Value36A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value36Assistant.buildQueryString(verified);
    }
}
