package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue23B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue23Assistant.buildQueryString(verified);
    }

}
