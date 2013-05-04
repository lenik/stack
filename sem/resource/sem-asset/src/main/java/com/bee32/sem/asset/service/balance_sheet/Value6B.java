package com.bee32.sem.asset.service.balance_sheet;


public class Value6B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value6Assistant.buildQueryString(verified);
    }

}
