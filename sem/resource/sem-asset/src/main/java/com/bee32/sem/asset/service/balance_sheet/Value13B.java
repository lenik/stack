package com.bee32.sem.asset.service.balance_sheet;


public class Value13B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value13Assistant.buildQueryString(verified);
    }

}
