package com.bee32.sem.asset.service.balance_sheet;


public class Value16B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value16Assistant.buildQueryString(verified);
    }

}
