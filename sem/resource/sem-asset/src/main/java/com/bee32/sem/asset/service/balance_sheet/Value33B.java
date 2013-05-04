package com.bee32.sem.asset.service.balance_sheet;


public class Value33B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value33Assistant.buildQueryString(verified);
    }

}
