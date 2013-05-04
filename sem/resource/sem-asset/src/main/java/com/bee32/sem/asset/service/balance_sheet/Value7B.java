package com.bee32.sem.asset.service.balance_sheet;


public class Value7B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value7Assistant.buildQueryString(verified);
    }

}
