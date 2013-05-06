package com.bee32.sem.asset.service.balance_sheet;


public class Value22A extends EndOfTermBalance {

    @Override
    String buildQueryString() {
        return Value22Assistant.buildQueryString(verified);
    }
}
