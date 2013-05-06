package com.bee32.sem.asset.service.balance_sheet;


public class Value17B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value17Assistant.buildQueryString(verified);
    }

}
