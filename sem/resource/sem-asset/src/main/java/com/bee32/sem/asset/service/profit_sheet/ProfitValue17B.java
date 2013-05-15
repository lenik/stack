package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue17B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue17Assistant.buildQueryString(verified);
    }

}
