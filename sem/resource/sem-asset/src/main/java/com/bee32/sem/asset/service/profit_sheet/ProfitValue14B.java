package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue14B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue14Assistant.buildQueryString(verified);
    }

}
