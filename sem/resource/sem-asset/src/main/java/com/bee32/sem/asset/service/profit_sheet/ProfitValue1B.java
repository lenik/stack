package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue1B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue1Assistant.buildQueryString(verified);
    }

}
