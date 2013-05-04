package com.bee32.sem.asset.service.balance_sheet;


public class Value9B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value9Assistant.buildQueryString(verified);
    }

}
