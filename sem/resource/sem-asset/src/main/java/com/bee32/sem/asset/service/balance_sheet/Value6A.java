package com.bee32.sem.asset.service.balance_sheet;


public class Value6A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value6Assistant.buildQueryString(verified);
    }
}
