package com.bee32.sem.asset.service.balance_sheet;


public class Value2A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value2Assistant.buildQueryString(verified);
    }
}
