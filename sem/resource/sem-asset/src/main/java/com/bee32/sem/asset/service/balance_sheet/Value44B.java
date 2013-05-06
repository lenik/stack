package com.bee32.sem.asset.service.balance_sheet;


public class Value44B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value44Assistant.buildQueryString(verified);
    }

}
