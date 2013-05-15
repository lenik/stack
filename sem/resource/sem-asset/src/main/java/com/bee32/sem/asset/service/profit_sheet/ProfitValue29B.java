package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue29B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue29Assistant.buildQueryString(verified);
    }

}
