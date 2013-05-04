package com.bee32.sem.asset.service.balance_sheet;


public class Value5A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value5Assistant.buildQueryString(verified);
    }
}
