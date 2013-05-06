package com.bee32.sem.asset.service.balance_sheet;


public class Value22B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value22Assistant.buildQueryString(verified);
    }

}
