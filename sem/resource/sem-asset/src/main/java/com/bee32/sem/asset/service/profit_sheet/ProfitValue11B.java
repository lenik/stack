package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue11B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue11Assistant.buildQueryString(verified);
    }

}
