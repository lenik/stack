package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue25B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue25Assistant.buildQueryString(verified);
    }

}
