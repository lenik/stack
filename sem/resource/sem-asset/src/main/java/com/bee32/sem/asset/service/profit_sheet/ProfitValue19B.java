package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue19B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue19Assistant.buildQueryString(verified);
    }

}
