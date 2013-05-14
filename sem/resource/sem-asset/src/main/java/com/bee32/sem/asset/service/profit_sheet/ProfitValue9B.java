package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue9B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue9Assistant.buildQueryString(verified);
    }

}
