package com.bee32.sem.asset.service.balance_sheet;


public class Value10B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value10Assistant.buildQueryString(verified);
    }

}
