package com.bee32.sem.asset.service.balance_sheet;


public class Value11B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value11Assistant.buildQueryString(verified);
    }

}
