package com.bee32.sem.asset.service.balance_sheet;


public class Value23A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value23Assistant.buildQueryString(verified);
    }
}
