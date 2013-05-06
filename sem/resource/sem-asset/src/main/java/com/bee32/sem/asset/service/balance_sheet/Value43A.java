package com.bee32.sem.asset.service.balance_sheet;


public class Value43A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value43Assistant.buildQueryString(verified);
    }
}
