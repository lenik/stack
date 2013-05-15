package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue26B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue26Assistant.buildQueryString(verified);
    }

}
