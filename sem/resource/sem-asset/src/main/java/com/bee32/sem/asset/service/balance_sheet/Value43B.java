package com.bee32.sem.asset.service.balance_sheet;


public class Value43B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value43Assistant.buildQueryString(verified);
    }

}
