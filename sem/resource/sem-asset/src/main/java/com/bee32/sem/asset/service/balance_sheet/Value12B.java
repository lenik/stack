package com.bee32.sem.asset.service.balance_sheet;


public class Value12B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value12Assistant.buildQueryString(verified);
    }

}
