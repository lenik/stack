package com.bee32.sem.asset.service.balance_sheet;


public class Value4A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value4Assistant.buildQueryString(verified);
    }
}
