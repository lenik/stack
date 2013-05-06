package com.bee32.sem.asset.service.balance_sheet;


public class Value37A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value37Assistant.buildQueryString(verified);
    }
}
