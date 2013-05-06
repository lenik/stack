package com.bee32.sem.asset.service.balance_sheet;


public class Value49A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value49Assistant.buildQueryString(verified);
    }
}
