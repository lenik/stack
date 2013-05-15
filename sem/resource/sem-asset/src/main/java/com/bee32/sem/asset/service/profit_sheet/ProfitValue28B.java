package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue28B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue28Assistant.buildQueryString(verified);
    }

}
