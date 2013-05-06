package com.bee32.sem.asset.service.balance_sheet;


public class Value32B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value32Assistant.buildQueryString(verified);
    }

}
