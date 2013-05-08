package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue3B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue3Assistant.buildQueryString(verified);
    }

}
