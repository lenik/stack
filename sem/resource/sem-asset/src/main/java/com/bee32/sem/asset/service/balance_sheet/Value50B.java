package com.bee32.sem.asset.service.balance_sheet;


public class Value50B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value50Assistant.buildQueryString(verified);
    }

}
