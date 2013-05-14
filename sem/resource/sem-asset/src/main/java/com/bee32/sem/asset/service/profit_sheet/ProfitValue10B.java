package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue10B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue10Assistant.buildQueryString(verified);
    }

}
