package com.bee32.sem.asset.service.balance_sheet;


public class Value1B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value1Assistant.buildQueryString(verified);
    }

}
