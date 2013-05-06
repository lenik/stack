package com.bee32.sem.asset.service.balance_sheet;


public class Value12A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value12Assistant.buildQueryString(verified);
    }
}
