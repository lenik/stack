package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue8B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue8Assistant.buildQueryString(verified);
    }

}
