package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue5B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue5Assistant.buildQueryString(verified);
    }

}
