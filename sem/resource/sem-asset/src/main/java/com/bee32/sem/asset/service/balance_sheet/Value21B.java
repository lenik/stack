package com.bee32.sem.asset.service.balance_sheet;


public class Value21B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value21Assistant.buildQueryString(verified);
    }

}
