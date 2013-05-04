package com.bee32.sem.asset.service.balance_sheet;


public class Value34B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value34Assistant.buildQueryString(verified);
    }

}
