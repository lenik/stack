package com.bee32.sem.asset.service.balance_sheet;


public class Value27A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value27Assistant.buildQueryString(verified);
    }
}
