package com.bee32.sem.asset.service.balance_sheet;


public class Value39A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value39Assistant.buildQueryString(verified);
    }
}
