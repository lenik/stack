package com.bee32.sem.asset.service.balance_sheet;


public class Value48B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value48Assistant.buildQueryString(verified);
    }

}
