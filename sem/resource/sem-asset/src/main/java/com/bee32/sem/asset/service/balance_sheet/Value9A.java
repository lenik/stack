package com.bee32.sem.asset.service.balance_sheet;


public class Value9A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value9Assistant.buildQueryString(verified);
    }
}
