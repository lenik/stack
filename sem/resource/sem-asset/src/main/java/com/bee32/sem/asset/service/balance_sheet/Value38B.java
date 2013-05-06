package com.bee32.sem.asset.service.balance_sheet;


public class Value38B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value38Assistant.buildQueryString(verified);
    }

}
