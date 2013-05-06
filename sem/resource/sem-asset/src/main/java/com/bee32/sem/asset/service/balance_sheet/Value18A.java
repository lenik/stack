package com.bee32.sem.asset.service.balance_sheet;


public class Value18A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value18Assistant.buildQueryString(verified);
    }
}
