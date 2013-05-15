package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue15B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue15Assistant.buildQueryString(verified);
    }

}
