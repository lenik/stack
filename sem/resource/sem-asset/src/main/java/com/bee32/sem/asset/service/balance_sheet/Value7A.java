package com.bee32.sem.asset.service.balance_sheet;


public class Value7A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value7Assistant.buildQueryString(verified);
    }
}
