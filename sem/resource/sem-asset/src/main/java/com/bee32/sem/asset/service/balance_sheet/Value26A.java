package com.bee32.sem.asset.service.balance_sheet;


public class Value26A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value26Assistant.buildQueryString(verified);
    }
}
