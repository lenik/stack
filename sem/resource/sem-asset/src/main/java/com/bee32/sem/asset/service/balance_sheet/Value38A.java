package com.bee32.sem.asset.service.balance_sheet;


public class Value38A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value38Assistant.buildQueryString(verified);
    }
}
