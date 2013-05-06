package com.bee32.sem.asset.service.balance_sheet;


public class Value16A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value16Assistant.buildQueryString(verified);
    }
}
