package com.bee32.sem.asset.service.balance_sheet;


public class Value42A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value42Assistant.buildQueryString(verified);
    }
}
