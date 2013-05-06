package com.bee32.sem.asset.service.balance_sheet;


public class Value23B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value23Assistant.buildQueryString(verified);
    }

}
