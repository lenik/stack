package com.bee32.sem.asset.service.balance_sheet;


public class Value3A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value3Assistant.buildQueryString(verified);
    }
}
