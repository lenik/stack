package com.bee32.sem.asset.service.profit_sheet;


public class ProfitValue13B extends ThisMonth {

    @Override
    String buildQueryString() {
        return ProfitValue13Assistant.buildQueryString(verified);
    }

}
