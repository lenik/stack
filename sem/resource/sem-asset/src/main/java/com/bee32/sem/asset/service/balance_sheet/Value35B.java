package com.bee32.sem.asset.service.balance_sheet;


public class Value35B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value35Assistant.buildQueryString(verified);
    }

}
