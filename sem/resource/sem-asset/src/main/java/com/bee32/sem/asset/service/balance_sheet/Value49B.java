package com.bee32.sem.asset.service.balance_sheet;


public class Value49B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value49Assistant.buildQueryString(verified);
    }

}
