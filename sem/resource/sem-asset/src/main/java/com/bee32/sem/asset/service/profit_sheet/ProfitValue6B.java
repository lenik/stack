package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue6B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue6Assistant.buildQueryString(verified);
    }

}
