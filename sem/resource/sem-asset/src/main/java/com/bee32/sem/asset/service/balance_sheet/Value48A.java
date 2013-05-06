package com.bee32.sem.asset.service.balance_sheet;


public class Value48A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value48Assistant.buildQueryString(verified);
    }
}
