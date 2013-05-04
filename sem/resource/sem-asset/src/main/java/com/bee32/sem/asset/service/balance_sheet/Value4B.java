package com.bee32.sem.asset.service.balance_sheet;


public class Value4B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value4Assistant.buildQueryString(verified);
    }

}
