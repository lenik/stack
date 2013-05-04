package com.bee32.sem.asset.service.balance_sheet;


public class Value33A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value33Assistant.buildQueryString(verified);
    }
}
