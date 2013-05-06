package com.bee32.sem.asset.service.balance_sheet;


public class Value19B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value19Assistant.buildQueryString(verified);
    }

}
