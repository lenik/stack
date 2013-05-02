package com.bee32.sem.asset.service.balance_sheet;


public class Value3B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value3Assistant.buildQueryString(verified);
    }

}
