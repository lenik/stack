package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue31B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue31Assistant.buildQueryString(verified);
    }

}
