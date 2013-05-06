package com.bee32.sem.asset.service.balance_sheet;


public class Value51A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value51Assistant.buildQueryString(verified);
    }
}
