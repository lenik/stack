package com.bee32.sem.asset.service.balance_sheet;


public class Value51B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value51Assistant.buildQueryString(verified);
    }

}
