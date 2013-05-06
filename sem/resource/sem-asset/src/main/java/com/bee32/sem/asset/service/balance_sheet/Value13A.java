package com.bee32.sem.asset.service.balance_sheet;


public class Value13A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value13Assistant.buildQueryString(verified);
    }
}
