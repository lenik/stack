package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue22B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue22Assistant.buildQueryString(verified);
    }

}
