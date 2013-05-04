package com.bee32.sem.asset.service.balance_sheet;


public class Value34A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value34Assistant.buildQueryString(verified);
    }
}
