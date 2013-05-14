package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue7B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue7Assistant.buildQueryString(verified);
    }

}
