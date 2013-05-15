package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue27B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue27Assistant.buildQueryString(verified);
    }

}
