package com.bee32.sem.asset.service.balance_sheet;


public class Value19A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value19Assistant.buildQueryString(verified);
    }
}
